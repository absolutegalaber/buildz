package org.absolutegalaber.buildz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import org.absolutegalaber.buildz.domain.BuildStats;
import org.absolutegalaber.buildz.domain.*;
import org.absolutegalaber.buildz.repository.BuildLabelRepository;
import org.absolutegalaber.buildz.repository.BuildRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Service
@Transactional
public class BuildService {
    @Inject
    private BuildRepository buildRepository;
    @Inject
    private BuildLabelRepository buildLabelRepository;


    public Optional<Build> byId(Long id) {
        return Optional.ofNullable(buildRepository.findOne(id));
    }

    public Build create(String project, String branch, Long buildNumber) {
        Build build = of(project, branch, buildNumber).orElse(
                new Build(project, branch, buildNumber)
        );
        return buildRepository.save(build);
    }

    public Build addLabels(Long buildId, List<BuildLabel> labels) throws InvalidRequestException {
        Build build = byId(buildId).orElseThrow(
                () -> new InvalidRequestException("No Build found for buildId=" + buildId)
        );
        labels.forEach(buildLabel -> {
            BuildLabel toAdd = new BuildLabel();
            toAdd.setKey(buildLabel.getKey());
            toAdd.setValue(buildLabel.getValue());
            toAdd.setBuild(build);
            build.getLabels().add(toAdd);
        });
        return buildRepository.save(build);
    }

//    public Optional<Build> latest(String project, String branch, String status) {
//        QBuild qBuildStatus = QBuild.buildStatus;
//        BooleanExpression where = qBuildStatus.project.eq(project)
//                .and(qBuildStatus.branch.eq(branch))
//                .and(qBuildStatus.status.eq(status));
//        Iterable<Build> all = buildRepository.findAll(where, qBuildStatus.buildNumber.desc());
//        Iterator<Build> iterator = all.iterator();
//        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
//    }


    private Optional<Build> of(String project, String branch, Long buildNumber) {
        QBuild qBuild = QBuild.build;
        BooleanExpression where = qBuild.project.eq(project)
                .and(qBuild.branch.eq(branch))
                .and(qBuild.buildNumber.eq(buildNumber));
        return Optional.ofNullable(buildRepository.findOne(where));
    }

    public BuildSearchResult search(BuildSearch search) {
        Set<BuildLabel> labelsToSearch = new HashSet<>();
        //a subquery for searches over build labels
        if (!search.getLabels().isEmpty()) {
            BooleanBuilder labelSearch = new BooleanBuilder();
            //a search over labels
            search.getLabels().forEach((key, value) -> {
                QBuildLabel theQBuildLabel = QBuildLabel.buildLabel;
                BooleanExpression aLabelPred = theQBuildLabel.key.eq(key).and(theQBuildLabel.value.eq(value));
                labelSearch.or(aLabelPred);
            });
            labelsToSearch = Sets.newHashSet(buildLabelRepository.findAll(labelSearch));
            if (labelsToSearch.isEmpty()) {
                //labels were searches, but no label was found ==> we can return an empty search result right now.
                return BuildSearchResult.emptyResult();
            }
        }
        QBuild theQBuild = QBuild.build;
        BooleanBuilder buildSearch = new BooleanBuilder();
        if (!Strings.isNullOrEmpty(search.getProject())) {
            buildSearch.and(theQBuild.project.eq(search.getProject()));
        }
        if (!Strings.isNullOrEmpty(search.getBranch())) {
            buildSearch.and(theQBuild.branch.eq(search.getBranch()));
        }
        if (search.getMinBuildNumber() != null) {
            buildSearch.and(theQBuild.buildNumber.gt(search.getMinBuildNumber()));
        }
        if (search.getMaxBuildNumber() != null) {
            buildSearch.and(theQBuild.buildNumber.lt(search.getMaxBuildNumber()));
        }
        if (!labelsToSearch.isEmpty()) {
            BooleanBuilder subQuery = new BooleanBuilder();
            labelsToSearch.forEach(
                    (label) -> subQuery.or(theQBuild.labels.contains(label))
            );
            buildSearch.and(subQuery);
        }
        return BuildSearchResult.fromPageResult(buildRepository.findAll(buildSearch, search.page()));
    }

    public BuildStats stats() {
        return new BuildStats(
                buildRepository.distinctProjects(),
                buildRepository.count(),
                buildLabelRepository.count()
        );
    }
}

package org.absolutegalaber.buildz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import org.absolutegalaber.buildz.domain.*;
import org.absolutegalaber.buildz.repository.BuildLabelRepository;
import org.absolutegalaber.buildz.repository.BuildRepository;
import org.absolutegalaber.buildz.repository.EnvironmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
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
    @Inject
    private EnvironmentRepository environmentRepository;


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
        try {
            Set<BuildLabel> labelsToSearch = labelsToInclude(search.getLabels());
            BooleanBuilder buildSearch = new BooleanBuilder();
            addProjectAndBranchPredicates(buildSearch, search.getProject(), search.getBranch());
            addMinMaxBuildNumberProedicates(buildSearch, search.getMinBuildNumber(), search.getMaxBuildNumber());
            addLabelsPredicates(buildSearch, labelsToSearch);
            return BuildSearchResult.fromPageResult(buildRepository.findAll(buildSearch, search.page()));
        } catch (EmptyLabelsSearchException e) {
            //labelsToInclude threw an exception because labels to search were specified but no labels could be found -> emtpy result
            return BuildSearchResult.emptyResult();
        }
    }

    public Optional<Build> latestArtifact(Artifact artifact) {
        try {
            Set<BuildLabel> buildLabels = labelsToInclude(artifact.getLabels());
            BooleanBuilder buildSearch = new BooleanBuilder();
            addProjectAndBranchPredicates(buildSearch, artifact.getProject(), artifact.getBranch());
            addLabelsPredicates(buildSearch, buildLabels);
            Page<Build> searchResult = buildRepository.findAll(buildSearch, new PageRequest(0, 3, new Sort(Sort.Direction.DESC, "buildNumber")));
            List<Build> foundBuilds = searchResult.getContent();
            return foundBuilds.isEmpty() ? Optional.empty() : Optional.of(foundBuilds.get(0));
        } catch (EmptyLabelsSearchException e) {
            return Optional.empty();
        }
    }

    private Set<BuildLabel> labelsToInclude(Map<String, String> labels) throws EmptyLabelsSearchException {
        if (!labels.isEmpty()) {
            BooleanBuilder labelSearch = new BooleanBuilder();
            //a search over labels
            labels.forEach((key, value) -> {
                QBuildLabel theQBuildLabel = QBuildLabel.buildLabel;
                BooleanExpression aLabelPredicate = theQBuildLabel.key.eq(key).and(theQBuildLabel.value.eq(value));
                labelSearch.or(aLabelPredicate);
            });
            Set<BuildLabel> labelsToSearch = Sets.newHashSet(buildLabelRepository.findAll(labelSearch));
            if (labelsToSearch.isEmpty()) {
                //labels were searches, but no label was found ==> we can return an empty search result right now.
                throw new EmptyLabelsSearchException();
            }
            //some labels were found, return them and proceed with any searches
            return labelsToSearch;
        }
        return Sets.newHashSet();
    }

    private void addProjectAndBranchPredicates(BooleanBuilder searchBuilder, String project, String branch) {
        //search for a project name
        if (!Strings.isNullOrEmpty(project)) {
            searchBuilder.and(QBuild.build.project.eq(project));
        }
        //search for a branch name
        if (!Strings.isNullOrEmpty(branch)) {
            searchBuilder.and(QBuild.build.branch.eq(branch));
        }
    }

    private void addMinMaxBuildNumberProedicates(BooleanBuilder searchBuilder, Long minBuild, Long maxBuild) {
        if (minBuild != null) {
            searchBuilder.and(QBuild.build.buildNumber.gt(minBuild));
        }
        if (maxBuild != null) {
            searchBuilder.and(QBuild.build.buildNumber.lt(maxBuild));
        }
    }

    private void addLabelsPredicates(BooleanBuilder searchBuilder, Set<BuildLabel> labels) {
        if (!labels.isEmpty()) {
            BooleanBuilder subQuery = new BooleanBuilder();
            labels.forEach(
                    (label) -> subQuery.or(QBuild.build.labels.contains(label))
            );
            searchBuilder.and(subQuery);
        }
    }

    public EnvironmentBuilds ofEnvironment(String environmentName) throws InvalidRequestException {
        Environment environment = environmentRepository.findByName(environmentName);
        if (environment == null) {
            throw new InvalidRequestException("No Environment found with name=" + environmentName);
        }
        EnvironmentBuilds builds = new EnvironmentBuilds();
        for (Artifact artifact : environment.getArtifacts()) {
            Optional<Build> build = latestArtifact(artifact);
            build.ifPresent(builds::add);
        }
        return builds;
    }
}

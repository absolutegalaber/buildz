package org.absolutegalaber.buildz.service;

import com.google.common.collect.Sets;
import org.absolutegalaber.buildz.domain.*;
import org.absolutegalaber.buildz.repository.BuildLabelRepository;
import org.absolutegalaber.buildz.repository.BuildRepository;
import org.absolutegalaber.buildz.repository.EnvironmentRepository;
import org.absolutegalaber.buildz.repository.EnvironmentSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.absolutegalaber.buildz.repository.BuildLabelSpecs.noBuildLabel;
import static org.absolutegalaber.buildz.repository.BuildLabelSpecs.withKeyAndValue;
import static org.absolutegalaber.buildz.repository.BuildSpecs.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Service
@Transactional
public class BuildService {
    private final BuildRepository buildRepository;
    private final BuildLabelRepository buildLabelRepository;
    private final EnvironmentRepository environmentRepository;

    public BuildService(BuildRepository buildRepository, BuildLabelRepository buildLabelRepository, EnvironmentRepository environmentRepository) {
        this.buildRepository = buildRepository;
        this.buildLabelRepository = buildLabelRepository;
        this.environmentRepository = environmentRepository;
    }

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

    private Optional<Build> of(String project, String branch, Long buildNumber) {
        QueryBuilder<Build> query = new QueryBuilder<Build>()
                .and(ofProject(project))
                .and(ofBranch(branch))
                .and(withBuildNumber(buildNumber));
        return Optional.ofNullable(buildRepository.findOne(query.theQuery()));
    }

    public BuildSearchResult search(BuildSearch search) {
        try {
            QueryBuilder<Build> queryBuilder = new QueryBuilder<>(anyBuild());
            Set<BuildLabel> labelsToSearch = labelsToInclude(search.getLabels());
            addProjectAndBranchPredicates(queryBuilder, search.getProject(), search.getBranch());
            addMinMaxBuildNumberPredicates(queryBuilder, search.getMinBuildNumber(), search.getMaxBuildNumber());
            addLabelsPredicates(queryBuilder, labelsToSearch);
            return BuildSearchResult.fromPageResult(buildRepository.findAll(queryBuilder.theQuery(), search.page()));
        } catch (EmptyLabelsSearchException e) {
            //labelsToInclude threw an exception because labels to search were specified but no labels could be found -> empty result
            return BuildSearchResult.emptyResult();
        }
    }

    public Optional<Build> latestArtifact(Artifact artifact) {
        try {
            Set<BuildLabel> buildLabels = labelsToInclude(artifact.getLabels());
            QueryBuilder<Build> buildSearch = new QueryBuilder<>(anyBuild());
            addProjectAndBranchPredicates(buildSearch, artifact.getProject(), artifact.getBranch());
            addLabelsPredicates(buildSearch, buildLabels);
            Page<Build> searchResult = buildRepository.findAll(buildSearch.theQuery(), new PageRequest(0, 3, new Sort(Sort.Direction.DESC, "buildNumber")));
            List<Build> foundBuilds = searchResult.getContent();
            return foundBuilds.isEmpty() ? Optional.empty() : Optional.of(foundBuilds.get(0));
        } catch (EmptyLabelsSearchException e) {
            return Optional.empty();
        }
    }

    private Set<BuildLabel> labelsToInclude(Map<String, String> labels) throws EmptyLabelsSearchException {
        if (!labels.isEmpty()) {
            QueryBuilder<BuildLabel> subQuery = new QueryBuilder<>(noBuildLabel());
            labels.forEach((key, val) -> subQuery.or(withKeyAndValue(key, val)));
            Set<BuildLabel> labelsToSearch = Sets.newHashSet(buildLabelRepository.findAll(subQuery.theQuery()));
            if (labelsToSearch.isEmpty()) {
                //labels were searches, but no label was found ==> we can return an empty search result right now.
                throw new EmptyLabelsSearchException();
            }
            //some labels were found, return them and proceed with any searches
            return labelsToSearch;
        }
        return Sets.newHashSet();
    }

    private void addProjectAndBranchPredicates(QueryBuilder<Build> queryBuilder, String project, String branch) {
        //search for a project name
        if (StringUtils.hasText(project)) {
            queryBuilder.and(ofProject(project));
        }
        //search for a branch name
        if (StringUtils.hasText(branch)) {
            queryBuilder.and(ofBranch(branch));
        }
    }

    private void addMinMaxBuildNumberPredicates(QueryBuilder<Build> buildSearch, Long minBuild, Long maxBuild) {
        if (minBuild != null) {
            buildSearch.and(withBuildNumberGreaterThan(minBuild));
        }
        if (maxBuild != null) {
            buildSearch.and(withBuildNumberLessThan(maxBuild));
        }
    }

    private void addLabelsPredicates(QueryBuilder<Build> buildSearch, Set<BuildLabel> labels) {
        if (!labels.isEmpty()) {
            QueryBuilder<Build> subQuery = new QueryBuilder<>(noBuilds());
            labels.forEach(buildLabel -> subQuery.or(hasLabel(buildLabel)));
            buildSearch.and(subQuery);
        }
    }

    public EnvironmentBuilds ofEnvironment(String environmentName) throws InvalidRequestException {
        Environment environment = environmentRepository.findOne(EnvironmentSpecs.environmentWithName(environmentName));
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

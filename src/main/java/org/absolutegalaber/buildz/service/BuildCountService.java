package org.absolutegalaber.buildz.service;

import org.absolutegalaber.buildz.domain.BuildCount;
import org.absolutegalaber.buildz.repository.BuildCountRepository;
import org.absolutegalaber.buildz.repository.BuildCountSpecs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Service
@Transactional
public class BuildCountService {
    private final BuildCountRepository buildCountRepository;

    public BuildCountService(BuildCountRepository buildCountRepository) {
        this.buildCountRepository = buildCountRepository;
    }

    public BuildCount next(String project, String branch) {
        BuildCount theCount = of(project, branch)
                .orElse(new BuildCount(project, branch));
        theCount.increment();
        return buildCountRepository.save(theCount);
    }

    public BuildCount current(String project, String branch) {
        return of(project, branch).orElse(new BuildCount(project, branch));
    }

    public void set(String project, String branch, Long buildCount) {
        BuildCount theCount = of(project, branch)
                .orElse(new BuildCount(project, branch));
        theCount.setCount(buildCount);
        buildCountRepository.save(theCount);
    }

    private Optional<BuildCount> of(String project, String branch) {
        QueryBuilder<BuildCount> querySpec = new QueryBuilder<BuildCount>()
                .and(BuildCountSpecs.buildCountOfProject(project))
                .and(BuildCountSpecs.buildCountOfBranch(branch));
        return Optional.ofNullable(buildCountRepository.findOne(querySpec.theQuery()));
    }
}

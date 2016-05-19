package org.absolutegalaber.buildz.service;

import com.mysema.query.types.expr.BooleanExpression;
import org.absolutegalaber.buildz.domain.BuildCount;
import org.absolutegalaber.buildz.domain.QBuildCount;
import org.absolutegalaber.buildz.repository.BuildCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Service
@Transactional
public class BuildCountService {
    @Inject
    protected BuildCountRepository buildCountRepository;

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
        QBuildCount buildCount = QBuildCount.buildCount;
        BooleanExpression where = buildCount.project.eq(project).and(
                buildCount.branch.eq(branch)
        );
        return Optional.ofNullable(buildCountRepository.findOne(where));
    }
}

package org.absolutegalaber.buildz.service;

import org.absolutegalaber.buildz.domain.BuildStats;
import org.absolutegalaber.buildz.repository.BuildLabelRepository;
import org.absolutegalaber.buildz.repository.BuildRepository;
import org.absolutegalaber.buildz.repository.EnvironmentRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Service
public class StatsService {
    private final BuildRepository buildRepository;
    private final BuildLabelRepository buildLabelRepository;
    private final EnvironmentRepository environmentRepository;

    public StatsService(BuildRepository buildRepository, BuildLabelRepository buildLabelRepository, EnvironmentRepository environmentRepository) {
        this.buildRepository = buildRepository;
        this.buildLabelRepository = buildLabelRepository;
        this.environmentRepository = environmentRepository;
    }

    public BuildStats stats() {
        return new BuildStats(
                buildRepository.distinctProjects(),
                environmentRepository.allEnvironmentNames(),
                buildRepository.count(),
                buildLabelRepository.count()
        );
    }

}

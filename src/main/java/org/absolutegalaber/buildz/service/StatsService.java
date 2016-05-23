package org.absolutegalaber.buildz.service;

import org.absolutegalaber.buildz.domain.BuildStats;
import org.absolutegalaber.buildz.repository.BuildLabelRepository;
import org.absolutegalaber.buildz.repository.BuildRepository;
import org.absolutegalaber.buildz.repository.EnvironmentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Service
public class StatsService {
    @Inject
    private BuildRepository buildRepository;
    @Inject
    private BuildLabelRepository buildLabelRepository;
    @Inject
    private EnvironmentRepository environmentRepository;

    public BuildStats stats() {
        return new BuildStats(
                buildRepository.distinctProjects(),
                environmentRepository.allEnvironmentNames(),
                buildRepository.count(),
                buildLabelRepository.count()
        );
    }

}

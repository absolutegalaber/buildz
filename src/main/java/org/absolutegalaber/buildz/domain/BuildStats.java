package org.absolutegalaber.buildz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Data
@NoArgsConstructor
public class BuildStats {
    public Set<String> projects = new HashSet<>();
    public Set<String> environments = new HashSet<>();
    public Long numberOfBuilds;
    public Long numberOfLabels;

    public BuildStats(Set<String> projects, Set<String> environments, Long numberOfBuilds, Long numberOfLabels) {
        this.projects = projects;
        this.environments = environments;
        this.numberOfBuilds = numberOfBuilds;
        this.numberOfLabels = numberOfLabels;
    }
}

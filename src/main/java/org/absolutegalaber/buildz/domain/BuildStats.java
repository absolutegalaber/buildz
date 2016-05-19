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
    public Long numberOfBuilds;
    public Long numberOfLabels;

    public BuildStats(Set<String> projects, Long numberOfBuilds, Long numberOfLabels) {
        this.projects = projects;
        this.numberOfBuilds = numberOfBuilds;
        this.numberOfLabels = numberOfLabels;
    }
}

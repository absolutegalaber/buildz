package org.absolutegalaber.buildz.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Data
public class EnvironmentBuilds {
    private Map<String, Build> builds = new HashMap<>();

    public void add(Build build) {
        builds.put(build.getProject(), build);
    }
}

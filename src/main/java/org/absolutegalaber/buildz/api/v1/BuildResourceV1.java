package org.absolutegalaber.buildz.api.v1;

import org.absolutegalaber.buildz.domain.*;
import org.absolutegalaber.buildz.service.BuildService;
import org.absolutegalaber.buildz.service.StatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@RestController
@RequestMapping(value = "v1/builds")
public class BuildResourceV1 {
    private final BuildService buildService;
    private final StatsService statsService;

    public BuildResourceV1(BuildService buildService, StatsService statsService) {
        this.buildService = buildService;
        this.statsService = statsService;
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public BuildStats stats() {
        return statsService.stats();
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public BuildSearchResult search(@RequestBody BuildSearch search) {
        return buildService.search(search);
    }

    @RequestMapping(value = "/{buildId}", method = RequestMethod.GET)
    public Build get(@PathVariable("buildId") Long buildId) throws InvalidRequestException {
        Optional<Build> build = buildService.byId(buildId);
        return build.orElseThrow(
                () -> new InvalidRequestException("No Build found for buildId=" + buildId)
        );
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Build create(@RequestBody Build build) {
        return buildService.create(build.getProject(), build.getBranch(), build.getBuildNumber());
    }

    @RequestMapping(value = "/addLabels/{buildId}", method = RequestMethod.POST)
    public Build addLabels(@PathVariable("buildId") Long buildId, @RequestBody List<BuildLabel> buildLabels) throws InvalidRequestException {
        return buildService.addLabels(buildId, buildLabels);
    }

    @RequestMapping(value = "/ofEnvironment/{name}", method = RequestMethod.GET)
    public EnvironmentBuilds environment(@PathVariable String name) throws InvalidRequestException {
        return buildService.ofEnvironment(name);
    }

}

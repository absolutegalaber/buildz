package org.absolutegalaber.buildz.api.v1;

import org.absolutegalaber.buildz.domain.Artifact;
import org.absolutegalaber.buildz.domain.Build;
import org.absolutegalaber.buildz.domain.Environment;
import org.absolutegalaber.buildz.domain.InvalidRequestException;
import org.absolutegalaber.buildz.service.BuildService;
import org.absolutegalaber.buildz.service.EnvironmentService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@RestController
@RequestMapping("v1/environments")
public class EnvironmentResourceV1 {
    private final EnvironmentService environmentService;
    private final BuildService buildService;

    public EnvironmentResourceV1(EnvironmentService environmentService, BuildService buildService) {
        this.environmentService = environmentService;
        this.buildService = buildService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Environment save(@RequestBody Environment environment) throws InvalidRequestException {
        return environmentService.save(environment);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Environment get(@PathVariable String name) throws InvalidRequestException {
        return environmentService.byName(name).orElseThrow(() -> new InvalidRequestException("No Environment found with name=" + name));
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public Set<Build> verifyEnvironment(@RequestBody Set<Artifact> artifacts) {
        Set<Build> toReturn = new HashSet<>();
        artifacts.forEach((artifact) -> {
            Optional<Build> build = buildService.latestArtifact(artifact);
            build.ifPresent(toReturn::add);
        });
        return toReturn;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String name) {
        environmentService.delete(name);
    }

}

package org.absolutegalaber.buildz.api.v1;

import org.absolutegalaber.buildz.domain.Artifact;
import org.absolutegalaber.buildz.domain.Environment;
import org.absolutegalaber.buildz.domain.InvalidRequestException;
import org.absolutegalaber.buildz.service.EnvironmentService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@RestController
@RequestMapping("v1/environments")
public class EnvironmentResourceV1 {
    @Inject
    private EnvironmentService environmentService;


    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Environment get(@PathVariable String name) throws InvalidRequestException {
        return environmentService.byName(name).orElseThrow(() -> new InvalidRequestException("No Environment found with name=" + name));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Environment create(@RequestBody Environment environment) throws InvalidRequestException {
        return environmentService.create(environment.getName());
    }

    @RequestMapping(value = "/addArtifact/{name}", method = RequestMethod.POST)
    public Environment addArtifact(@PathVariable("name") String name, @RequestBody Artifact artifact) throws InvalidRequestException {
        return environmentService.addArtifact(name, artifact);
    }
}

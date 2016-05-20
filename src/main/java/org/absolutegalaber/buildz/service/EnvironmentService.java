package org.absolutegalaber.buildz.service;

import org.absolutegalaber.buildz.domain.Artifact;
import org.absolutegalaber.buildz.domain.Environment;
import org.absolutegalaber.buildz.domain.InvalidRequestException;
import org.absolutegalaber.buildz.repository.EnvironmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Service
@Transactional
public class EnvironmentService {
    @Inject
    private EnvironmentRepository environmentRepository;

    public Optional<Environment> byName(String name) {
        return Optional.ofNullable(environmentRepository.findByName(name));
    }

    public Environment create(String name) throws InvalidRequestException {
        //check name to avoid unique value constraint failure from db.
        if (byName(name).isPresent()) {
            throw new InvalidRequestException("Environment with name=" + name + "already present");
        }
        Environment environment = new Environment();
        environment.setName(name);
        return environmentRepository.save(environment);
    }

    public Environment addArtifact(String name, Artifact artifact) throws InvalidRequestException {
        Environment environment = byName(name).orElseThrow(() -> new InvalidRequestException("No Environment found for name=" + name));
        artifact.setEnvironment(environment);
        environment.getArtifacts().add(artifact);
        return environmentRepository.save(environment);
    }
}

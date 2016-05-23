package org.absolutegalaber.buildz.service;

import org.absolutegalaber.buildz.domain.Artifact;
import org.absolutegalaber.buildz.domain.Environment;
import org.absolutegalaber.buildz.domain.InvalidRequestException;
import org.absolutegalaber.buildz.repository.ArtifactRepository;
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
    @Inject
    private ArtifactRepository artifactRepository;

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

    public Environment save(Environment environment) throws InvalidRequestException {
        if (environment.getId() != null) {
            //an update ==> we don't mess around here and clean the depending side environment.artifact
            //obviously this could be handled better...
            Environment current = environmentRepository.findOne(environment.getId());
            current.getArtifacts().forEach((artifact) -> artifactRepository.delete(artifact));
            current.getArtifacts().clear();
        } else {
            //an insert -> check uniqueness of name
            if (byName(environment.getName()).isPresent()) {
                throw new InvalidRequestException("Environment with name=" + environment.getName() + "already present");
            }
        }
        return environmentRepository.save(environment);
    }
}

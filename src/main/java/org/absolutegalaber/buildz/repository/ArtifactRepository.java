package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.Artifact;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface ArtifactRepository extends PagingAndSortingRepository<Artifact, Long> {
}

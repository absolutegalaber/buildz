package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.BuildCount;
import org.absolutegalaber.buildz.domain.BuildCount_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class BuildCountSpecs {
    public static Specification<BuildCount> buildCountOfProject(String project) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BuildCount_.project), project);
    }

    public static Specification<BuildCount> buildCountOfBranch(String branch) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BuildCount_.branch), branch);
    }
}

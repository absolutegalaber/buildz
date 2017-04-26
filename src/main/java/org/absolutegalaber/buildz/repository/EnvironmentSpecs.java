package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.Environment;
import org.absolutegalaber.buildz.domain.Environment_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class EnvironmentSpecs {
    public static Specification<Environment> environmentWithName(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Environment_.name), name);
    }
}

package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.BuildLabel;
import org.absolutegalaber.buildz.domain.BuildLabel_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class BuildLabelSpecs {
    public static Specification<BuildLabel> allLabels() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<BuildLabel> noBuildLabel() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.disjunction();
    }

    public static Specification<BuildLabel> withKeyAndValue(String key, String value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(BuildLabel_.key), key),
                        criteriaBuilder.equal(root.get(BuildLabel_.value), value)
                );
    }

    public static Specification<BuildLabel> withKey(String key) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BuildLabel_.key), key);
    }

    public static Specification<BuildLabel> withValue(String value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BuildLabel_.value), value);
    }
}

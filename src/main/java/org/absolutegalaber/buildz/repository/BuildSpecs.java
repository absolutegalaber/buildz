package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.Build;
import org.absolutegalaber.buildz.domain.BuildLabel;
import org.absolutegalaber.buildz.domain.Build_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class BuildSpecs {
    public static Specification<Build> allBuilds() {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.conjunction();
    }

    public static Specification<Build> noBuilds() {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.disjunction();
    }

    public static Specification<Build> ofProject(String projectName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Build_.project), projectName);
    }

    public static Specification<Build> ofBranch(String branchName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Build_.branch), branchName);
    }

    public static Specification<Build> withBuildNumber(Long buildNumber) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Build_.buildNumber), buildNumber);
    }

    public static Specification<Build> withBuildNumberGreaterThan(Long minBuildNumber) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.gt(root.get(Build_.buildNumber), minBuildNumber);
    }

    public static Specification<Build> withBuildNumberLessThan(Long maxBuildNumber) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lt(root.get(Build_.buildNumber), maxBuildNumber);
    }

    public static Specification<Build> hasLabel(BuildLabel label) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.isMember(label, root.get(Build_.labels));
    }
}

package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.Build;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface BuildRepository extends PagingAndSortingRepository<Build, Long>, JpaSpecificationExecutor<Build> {
    @Query("SELECT DISTINCT b.project FROM Build b ORDER BY b.project ASC")
    Set<String> distinctProjects();
}

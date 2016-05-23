package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.Environment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface EnvironmentRepository extends PagingAndSortingRepository<Environment, Long>, QueryDslPredicateExecutor<Environment> {
    public Environment findByName(String name);

    @Query("SELECT e.name FROM Environment e ORDER BY e.name ASC")
    Set<String> allEnvironmentNames();

}

package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.Environment;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface EnvironmentRepository extends PagingAndSortingRepository<Environment, Long>, QueryDslPredicateExecutor<Environment> {
    public Environment findByName(String name);
}

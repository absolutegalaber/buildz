package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.BuildCount;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface BuildCountRepository extends CrudRepository<BuildCount, Long>, QueryDslPredicateExecutor<BuildCount> {
}

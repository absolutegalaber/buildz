package org.absolutegalaber.buildz.repository;

import org.absolutegalaber.buildz.domain.BuildLabel;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface BuildLabelRepository extends PagingAndSortingRepository<BuildLabel, Long>, QueryDslPredicateExecutor<BuildLabel> {
}

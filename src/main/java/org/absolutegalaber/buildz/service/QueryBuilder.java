package org.absolutegalaber.buildz.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class QueryBuilder<T> {
    private Specifications<T> theQuery;

    public QueryBuilder(Specification<T> initial) {
        this.theQuery = Specifications.where(initial);
    }

    public QueryBuilder() {
    }

    public QueryBuilder<T> and(Specification<T> specification) {
        if (theQuery != null) {
            theQuery = theQuery.and(specification);
        } else {
            theQuery = Specifications.where(specification);
        }
        return this;
    }

    public QueryBuilder<T> and(QueryBuilder<T> subQuery) {
        if (theQuery != null) {
            theQuery = theQuery.and(subQuery.theQuery());
        } else {
            theQuery = Specifications.where(subQuery.theQuery());
        }
        return this;
    }

    public QueryBuilder<T> or(Specification<T> specification) {
        if (theQuery != null) {
            theQuery = theQuery.or(specification);
        } else {
            theQuery = Specifications.where(specification);
        }
        return this;
    }

    public QueryBuilder<T> or(QueryBuilder<T> subQuery) {
        if (theQuery != null) {
            theQuery = theQuery.or(subQuery.theQuery());
        } else {
            theQuery = Specifications.where(subQuery.theQuery());
        }
        return this;
    }

    public Specifications<T> theQuery() {
        return theQuery;
    }
}

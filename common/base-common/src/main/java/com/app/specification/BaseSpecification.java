package com.app.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.app.criteria.BaseCriteria;
import com.app.entity.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BaseSpecification<T extends BaseEntity> implements Specification<T> {

    private final BaseCriteria criteria;

    public BaseSpecification(BaseCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtra por ID
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        // Filtra por ownerId
        if (criteria.getOwnerId() != null) {
            predicates.add(cb.equal(root.get("ownerId"), criteria.getOwnerId()));
        }

        // Aplica ordenação
        if (criteria.getSort() != null && !criteria.getSort().isEmpty()) {
            String orderDirection = "ASC".equalsIgnoreCase(criteria.getOrder()) ? "ASC" : "DESC";
            query.orderBy("ASC".equals(orderDirection)
                    ? cb.asc(root.get(criteria.getSort()))
                    : cb.desc(root.get(criteria.getSort())));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
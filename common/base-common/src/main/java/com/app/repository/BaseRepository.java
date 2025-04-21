package com.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import com.app.criteria.BaseCriteria;
import com.app.entity.BaseEntity;
import com.app.specification.BaseSpecification;
import com.app.utils.SecurityUtils;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    default Page<T> findAll(BaseCriteria criteria) {
        Specification<T> spec = new BaseSpecification<>(criteria);
        return findAll(spec, criteria.getPageable());
    }

    @NonNull
    default Optional<T> findById(Long id) {
        return findById(id).filter(entity -> entity.isOwnedBy(
                SecurityUtils.getCurrentUser().getId()));
    }
}

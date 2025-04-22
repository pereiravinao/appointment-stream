package com.app.repository;

import java.util.List;
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

    default Page<T> findAllBase(BaseCriteria criteria) {
        Specification<T> spec = new BaseSpecification<>(criteria);
        return findAll(spec, criteria.getPageable());
    }

    @NonNull
    default List<T> findAllBase() {
        return this.findAllByOwnerId(SecurityUtils.getOwnerId());
    }

    @NonNull
    default Optional<T> findByIdBase(Long id) {
        var ownerId = SecurityUtils.getOwnerId();
        if (ownerId != null) {
            return findById(id).filter(entity -> entity.isOwnedBy(ownerId));
        }
        return findById(id);
    }

    default T saveBase(T entity) {
        entity.setOwnerId(SecurityUtils.getOwnerId());
        return save(entity);
    }

    List<T> findAllByOwnerId(Long ownerId);
}

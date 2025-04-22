package com.app.criteria;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.app.utils.SecurityUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCriteria {
    private Long id;
    private Integer page = 0;
    private Integer size = 10;
    private String sort = "createdAt";
    private String order = "desc";

    public Pageable getPageable() {
        return PageRequest.of(page, size, Sort.by(sort).descending());
    }

    public Long getOwnerId() {
        return SecurityUtils.getOwnerId();
    }

}

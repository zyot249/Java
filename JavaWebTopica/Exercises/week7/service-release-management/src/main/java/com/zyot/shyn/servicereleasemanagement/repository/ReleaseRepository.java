package com.zyot.shyn.servicereleasemanagement.repository;

import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReleaseRepository extends PagingAndSortingRepository<ReleaseEntity, String> {
    Page<ReleaseEntity> findByCreatedby(String createdby, Pageable pageable);
}

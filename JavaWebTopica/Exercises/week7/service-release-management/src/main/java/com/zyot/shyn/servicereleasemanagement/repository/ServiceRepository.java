package com.zyot.shyn.servicereleasemanagement.repository;

import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ServiceRepository extends PagingAndSortingRepository<ServiceEntity, String> {
    Page<ServiceEntity> findDistinctByEnvironmentAndNamespace(String environment, String namespace, Pageable pageable);

    Page<ServiceEntity> findAllByReleaseByReleaseidId(String releaseId, Pageable pageable);

    Optional<ServiceEntity> findByIdAndReleaseByReleaseidId(String id, String releaseId);

    Page<ServiceEntity> findAllByName(String name, Pageable pageable);
}

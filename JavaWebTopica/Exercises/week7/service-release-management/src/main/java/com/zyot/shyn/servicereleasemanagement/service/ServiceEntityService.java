package com.zyot.shyn.servicereleasemanagement.service;

import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceEntityService {
    ServiceEntity save(ServiceEntity serviceEntity);

    Optional<ServiceEntity> findById(String id);

    void delete(String id);

    void delete(ServiceEntity service);

    Page<ServiceEntity> findAll(Pageable pageable);
}

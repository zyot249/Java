package com.zyot.shyn.servicereleasemanagement.service;

import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReleaseEntityService {
    ReleaseEntity save(ReleaseEntity release);

    Optional<ReleaseEntity> findById(String id);

    void delete(String id);

    void delete(ReleaseEntity release);

    Page<ReleaseEntity> findByCreator(String name, Pageable pageable);

    Page<ReleaseEntity> findAll(Pageable pageable);
}

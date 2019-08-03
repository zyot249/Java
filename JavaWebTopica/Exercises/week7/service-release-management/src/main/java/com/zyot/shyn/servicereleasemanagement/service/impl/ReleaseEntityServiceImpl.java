package com.zyot.shyn.servicereleasemanagement.service.impl;

import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import com.zyot.shyn.servicereleasemanagement.repository.ReleaseRepository;
import com.zyot.shyn.servicereleasemanagement.service.ReleaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReleaseEntityServiceImpl implements ReleaseEntityService {
    @Autowired
    private ReleaseRepository releaseRepository;

    @Override
    public ReleaseEntity save(ReleaseEntity release) {
        return releaseRepository.save(release);
    }

    @Override
    public Optional<ReleaseEntity> findById(String id) {
        return releaseRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        releaseRepository.deleteById(id);
    }

    @Override
    public void delete(ReleaseEntity release) {
        releaseRepository.delete(release);
    }

    @Override
    public Page<ReleaseEntity> findByCreator(String name, Pageable pageable) {
        return releaseRepository.findByCreatedby(name, pageable);
    }

    @Override
    public Page<ReleaseEntity> findAll(Pageable pageable) {
        return releaseRepository.findAll(pageable);
    }

    @Override
    public boolean existsById(String releaseId) {
        return releaseRepository.existsById(releaseId);
    }
}

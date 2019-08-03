package com.zyot.shyn.servicereleasemanagement.service.impl;

import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
import com.zyot.shyn.servicereleasemanagement.repository.ServiceRepository;
import com.zyot.shyn.servicereleasemanagement.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceEntityServiceImpl implements ServiceEntityService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public ServiceEntity save(ServiceEntity serviceEntity) {
        return serviceRepository.save(serviceEntity);
    }

    @Override
    public Optional<ServiceEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public void delete(ServiceEntity service) {
        serviceRepository.delete(service);
    }

    @Override
    public Page<ServiceEntity> findAll(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }

    @Override
    public Page<ServiceEntity> findAllByEnvironmentAndNamespace(String environment, String namespace, Pageable pageable) {
        return serviceRepository.findAllByEnvironmentAndNamespace(environment, namespace, pageable);
    }

    @Override
    public Page<ServiceEntity> findAllServicesByReleaseId(String releaseId, Pageable pageable) {
        return serviceRepository.findAllByReleaseByReleaseidId(releaseId, pageable);
    }

    @Override
    public Optional<ServiceEntity> findByIdAndReleaseId(String id, String releaseId) {
        return serviceRepository.findByIdAndReleaseByReleaseidId(id, releaseId);
    }
}

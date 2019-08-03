package com.zyot.shyn.servicereleasemanagement.controller;

import com.zyot.shyn.servicereleasemanagement.common.Messages;
import com.zyot.shyn.servicereleasemanagement.exception.ResourceNotFoundException;
import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
import com.zyot.shyn.servicereleasemanagement.model.criteria.ServiceCriteria;
import com.zyot.shyn.servicereleasemanagement.service.ReleaseEntityService;
import com.zyot.shyn.servicereleasemanagement.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ServiceController {
    @Autowired
    private ServiceEntityService serviceEntityService;

    @Autowired
    private ReleaseEntityService releaseEntityService;

    // API
    @GetMapping("/api/service/get-by-env-and-nsp")
    public Page<ServiceEntity> getListOfServicesByEnvironmentAndNamespace(@RequestParam(name = "environment") String environment,
                                                                          @RequestParam(name = "namespace") String namespace,
                                                                          Pageable pageable) {
        return serviceEntityService.findAllByEnvironmentAndNamespace(environment, namespace, pageable);
    }

    @GetMapping("/api/service/get-list-versions-by-service")
    public Page<String> getListOfVersionsByService(@RequestParam(name = "name") String name,
                                                   Pageable pageable) {
        return serviceEntityService.getListOfVersionsByServiceName(name, pageable);
    }

    // CRUD
    @GetMapping("/release/{releaseId}/service")
    public Page<ServiceEntity> getAllServicesByReleaseId(@PathVariable("releaseId") String releaseId,
                                                         Pageable pageable) {
        return serviceEntityService.findAllServicesByReleaseId(releaseId, pageable);
    }

    @GetMapping("/release/{releaseId}/service/{serviceId}")
    public ServiceEntity getServiceByID(@PathVariable("releaseId") String releaseId,
                                        @PathVariable("serviceId") String serviceId) {
        if (!releaseEntityService.existsById(releaseId))
            throw new ResourceNotFoundException(String.format(Messages.MSG_RELEASE_NOT_FOUND_FORMAT, releaseId));
        return serviceEntityService.findById(serviceId).orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.MSG_SERVICE_NOT_FOUND_FORMAT, serviceId)));
    }

    @PostMapping("/release/{releaseId}/service")
    public ServiceEntity addService(@PathVariable("releaseId") String releaseId,
                                    @Valid @RequestBody ServiceCriteria serviceCriteria) {
        return releaseEntityService.findById(releaseId).map(release ->
                serviceEntityService.save(new ServiceEntity(serviceCriteria, release))
        ).orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.MSG_RELEASE_NOT_FOUND_FORMAT, releaseId)));
    }

    @PutMapping("/release/{releaseId}/service/{serviceId}")
    public ServiceEntity updateService(@PathVariable("releaseId") String releaseId,
                                       @PathVariable("serviceId") String serviceId,
                                       @Valid @RequestBody ServiceCriteria serviceCriteria) {
        if (!releaseEntityService.existsById(releaseId))
            throw new ResourceNotFoundException(String.format(Messages.MSG_RELEASE_NOT_FOUND_FORMAT, releaseId));
        return serviceEntityService.findById(serviceId).map(service -> {
            service.setName(serviceCriteria.getName());
            service.setEnvironment(serviceCriteria.getEnvironment());
            service.setNamespace(serviceCriteria.getNamespace());
            service.setOldversion(serviceCriteria.getOldversion());
            service.setNewversion(serviceCriteria.getNewversion());
            return serviceEntityService.save(service);
        }).orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.MSG_SERVICE_NOT_FOUND_FORMAT, serviceId)));
    }

    @DeleteMapping("/release/{releaseId}/service/{serviceId}")
    public ResponseEntity deleteService(@PathVariable("releaseId") String releaseId,
                                        @PathVariable("serviceId") String serviceId) {
        return serviceEntityService.findByIdAndReleaseId(serviceId, releaseId).map(service -> {
            serviceEntityService.delete(service);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Service not found with id:" + serviceId + " and releaseId:" + releaseId));
    }
}

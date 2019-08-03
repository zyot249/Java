package com.zyot.shyn.servicereleasemanagement.controller;

import com.zyot.shyn.servicereleasemanagement.exception.ResourceNotFoundException;
import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
import com.zyot.shyn.servicereleasemanagement.model.criteria.ServiceCriteria;
import com.zyot.shyn.servicereleasemanagement.service.ReleaseEntityService;
import com.zyot.shyn.servicereleasemanagement.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ServiceController {
    @Autowired
    private ServiceEntityService serviceEntityService;

    @Autowired
    private ReleaseEntityService releaseEntityService;

    @GetMapping("/api/service/{id}")
    public Optional<ServiceEntity> findById(@PathVariable("id") String id) {
        if (id != null) {
            return serviceEntityService.findById(id);
        } else return Optional.empty();
    }

    @GetMapping("/api/service/delete/{id}")
    public String deleteById(@PathVariable String id) {
        if (id != null) {
            serviceEntityService.delete(id);
            return "successfully";
        } else return "Id must be not null";
    }

    @GetMapping("/api/service/list")
    public Page<ServiceEntity> findAll() {
        return serviceEntityService.findAll(new PageRequest(0, 10));
    }

    @PostMapping("/api/service/create")
    public ServiceEntity addNewService(@RequestParam(name = "name") String name,
                                       @RequestParam(name = "environment") String environment,
                                       @RequestParam(name = "namespace") String namespace,
                                       @RequestParam(name = "oldVersion") String oldVersion,
                                       @RequestParam(name = "newVersion") String newVersion,
                                       @RequestParam(name = "releaseId") String releaseId) {
        Optional<ReleaseEntity> releaseEntity = releaseEntityService.findById(releaseId);
        return releaseEntity.map(entity -> serviceEntityService.save(new ServiceEntity(name,
                environment,
                namespace,
                oldVersion,
                newVersion,
                entity))).orElse(null);

    }

    @GetMapping("/api/service/get-by-env-and-nsp")
    public Page<ServiceEntity> getListOfServicesByEnvironmentAndNamespace(@RequestParam(name = "environment") String environment,
                                                                          @RequestParam(name = "namespace") String namespace,
                                                                          Pageable pageable) {
        return serviceEntityService.findAllByEnvironmentAndNamespace(environment, namespace, pageable);
    }

    @GetMapping("/api/service/get-list-versions-by-service/{name}")
    public Page<String> getListOfVersionsByService(@PathVariable("name") String name,
                                                   Pageable pageable) {
        return serviceEntityService.getListOfVersionsByServiceName(name, pageable);
    }

    // CRUD
    @GetMapping("/release/{releaseId}/service")
    public Page<ServiceEntity> getAllServicesByReleaseId(@PathVariable("releaseId") String releaseId,
                                                         Pageable pageable) {
        return serviceEntityService.findAllServicesByReleaseId(releaseId, pageable);
    }

    @PostMapping("/release/{releaseId}/service")
    public ServiceEntity addService(@PathVariable("releaseId") String releaseId,
                                    @Valid @RequestBody ServiceCriteria serviceCriteria) {
        return releaseEntityService.findById(releaseId).map(release ->
                serviceEntityService.save(new ServiceEntity(serviceCriteria, release))
        ).orElseThrow(() -> new ResourceNotFoundException("Release with id:" + releaseId + " not found!"));
    }

    @PutMapping("/release/{releaseId}/service/{serviceId}")
    public ServiceEntity updateService(@PathVariable("releaseId") String releaseId,
                                       @PathVariable("serviceId") String serviceId,
                                       @Valid @RequestBody ServiceCriteria serviceCriteria) {
        if (!releaseEntityService.existsById(releaseId))
            throw new ResourceNotFoundException("Release with id:" + releaseId + " not found!");
        return serviceEntityService.findById(serviceId).map(service -> {
            service.setName(serviceCriteria.getName());
            service.setEnvironment(serviceCriteria.getEnvironment());
            service.setNamespace(serviceCriteria.getNamespace());
            service.setOldversion(serviceCriteria.getOldversion());
            service.setNewversion(serviceCriteria.getNewversion());
            return serviceEntityService.save(service);
        }).orElseThrow(() -> new ResourceNotFoundException("Service with id:" + serviceId + " not found!"));
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

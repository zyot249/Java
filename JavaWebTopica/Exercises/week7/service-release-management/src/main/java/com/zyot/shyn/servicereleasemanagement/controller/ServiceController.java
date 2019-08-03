package com.zyot.shyn.servicereleasemanagement.controller;

import com.zyot.shyn.servicereleasemanagement.exception.ResourceNotFoundException;
import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
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

    // CRUD
    @GetMapping("/release/{releaseId}/service")
    public Page<ServiceEntity> getAllServicesByReleaseId(@PathVariable("releaseId") String releaseId,
                                                         Pageable pageable) {
        return serviceEntityService.findAllServicesByReleaseId(releaseId, pageable);
    }

    @PostMapping("/release/{releaseId}/service")
    public ServiceEntity addService(@PathVariable("releaseId") String releaseId,
                                    @Valid @RequestBody ServiceEntity serviceEntity) {
        return releaseEntityService.findById(releaseId).map(release -> {
            serviceEntity.setReleaseByReleaseid(release);
            return serviceEntityService.save(serviceEntity);
        }).orElseThrow(() -> new ResourceNotFoundException("Release with id:" + releaseId + " not found!"));
    }

    @PutMapping("/release/{releaseId}/service/{serviceId}")
    public ServiceEntity updateService(@PathVariable("releaseId") String releaseId,
                                       @PathVariable("serviceId") String serviceId,
                                       @Valid @RequestBody ServiceEntity serviceEntity) {
        if (!releaseEntityService.existsById(releaseId))
            throw new ResourceNotFoundException("Release with id:" + releaseId + " not found!");
        return serviceEntityService.findById(serviceId).map(service -> {
            service.setName(serviceEntity.getName());
            service.setEnvironment(serviceEntity.getEnvironment());
            service.setNamespace(serviceEntity.getNamespace());
            service.setOldversion(serviceEntity.getOldversion());
            service.setNewversion(serviceEntity.getNewversion());
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

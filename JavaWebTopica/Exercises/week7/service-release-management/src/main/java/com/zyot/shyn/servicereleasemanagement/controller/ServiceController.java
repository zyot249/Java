package com.zyot.shyn.servicereleasemanagement.controller;

import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
import com.zyot.shyn.servicereleasemanagement.service.ReleaseEntityService;
import com.zyot.shyn.servicereleasemanagement.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    private ServiceEntityService serviceEntityService;

    @Autowired
    private ReleaseEntityService releaseEntityService;

    @GetMapping("/{id}")
    public Optional<ServiceEntity> findById(@PathVariable String id) {
        if (id != null) {
            return serviceEntityService.findById(id);
        } else return Optional.empty();
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable String id) {
        if (id != null) {
            serviceEntityService.delete(id);
            return "successfully";
        } else return "Id must be not null";
    }

    @GetMapping("/list")
    public Page<ServiceEntity> findAll() {
        return serviceEntityService.findAll(new PageRequest(0, 10));
    }

    @PostMapping("/create")
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
}

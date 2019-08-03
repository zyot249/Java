package com.zyot.shyn.servicereleasemanagement.controller;

import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import com.zyot.shyn.servicereleasemanagement.model.criteria.ReleaseCriteria;
import com.zyot.shyn.servicereleasemanagement.service.ReleaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/release")
public class ReleaseController {
    @Autowired
    private ReleaseEntityService releaseEntityService;

    @PostMapping(value = "/create")
    public ReleaseEntity addNewRelease(@RequestParam(name = "name") String name,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "createdBy") String createdBy) {
        ReleaseEntity release = new ReleaseEntity(new ReleaseCriteria(name, description, createdBy));
        return releaseEntityService.save(release);

    }

    @GetMapping("/create")
    public String testCreate() {
        return "successfully";
    }

    @PostMapping("/find-by-creator")
    public Page<ReleaseEntity> findAllByCreator(@RequestParam(name = "name") String name) {
        return releaseEntityService.findByCreator(name, new PageRequest(0, 10));
    }

    @GetMapping("/{id}")
    public Optional<ReleaseEntity> findById(@PathVariable String id) {
        if (id != null)
            return releaseEntityService.findById(id);
        else return null;
    }

    @GetMapping("/delete/{id}")
    public String deleteRelease(@PathVariable String id) {
        if (id != null) {
            releaseEntityService.delete(id);
            return "successfully";
        } else return "Id must be not null";
    }

    @GetMapping("/list")
    public Page<ReleaseEntity> findAll() {
        return releaseEntityService.findAll(new PageRequest(0, 10));
    }
}

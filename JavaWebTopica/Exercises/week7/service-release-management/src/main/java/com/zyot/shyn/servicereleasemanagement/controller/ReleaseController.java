package com.zyot.shyn.servicereleasemanagement.controller;

import com.zyot.shyn.servicereleasemanagement.common.Messages;
import com.zyot.shyn.servicereleasemanagement.exception.ResourceNotFoundException;
import com.zyot.shyn.servicereleasemanagement.model.ReleaseEntity;
import com.zyot.shyn.servicereleasemanagement.model.criteria.ReleaseCriteria;
import com.zyot.shyn.servicereleasemanagement.service.ReleaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ReleaseController {
    @Autowired
    private ReleaseEntityService releaseEntityService;

    // test
    @PostMapping(value = "/api/release/create")
    public ReleaseEntity addNewRelease(@RequestParam(name = "name") String name,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "createdBy") String createdBy) {
        ReleaseEntity release = new ReleaseEntity(new ReleaseCriteria(name, description, createdBy));
        return releaseEntityService.save(release);

    }

    @GetMapping("/api/release/create")
    public String testCreate() {
        return "successfully";
    }

    @PostMapping("/api/release/find-by-creator")
    public Page<ReleaseEntity> findAllByCreator(@RequestParam(name = "name") String name,
                                                Pageable pageable) {
        return releaseEntityService.findByCreator(name, pageable);
    }

    @GetMapping("/api/release/{id}")
    public Optional<ReleaseEntity> findById(@PathVariable String id) {
        if (id != null)
            return releaseEntityService.findById(id);
        else return Optional.empty();
    }

    @GetMapping("/api/release/delete/{id}")
    public String deleteReleaseById(@PathVariable String id) {
        if (id != null) {
            releaseEntityService.delete(id);
            return "successfully";
        } else return "Id must be not null";
    }

    @GetMapping("/api/release/list")
    public Page<ReleaseEntity> findAll(Pageable pageable) {
        return releaseEntityService.findAll(pageable);
    }

    //CRUD

    @PostMapping("/release")
    public ReleaseEntity createRelease(@Valid @RequestBody ReleaseCriteria releaseCriteria) {
        return releaseEntityService.save(new ReleaseEntity(releaseCriteria));
    }

    @GetMapping("/release")
    public Page<ReleaseEntity> getAllReleases(Pageable pageable) {
        return releaseEntityService.findAll(pageable);
    }

    @PutMapping("/release/{id}")
    public ReleaseEntity updateRelease(@PathVariable("id") String id,
                                       @Valid @RequestBody ReleaseCriteria releaseCriteria) {
        return releaseEntityService.findById(id).map(release -> {
            release.setName(releaseCriteria.getName());
            release.setDescription(releaseCriteria.getDescription());
            release.setCreatedby(releaseCriteria.getCreatedby());
            return releaseEntityService.save(release);
        }).orElseThrow(() -> new ResourceNotFoundException("Release with id:" + id + Messages.MSG_NOTFOUND));
    }

    @DeleteMapping("/release/{id}")
    public ResponseEntity deleteRelease(@PathVariable("id") String id) {
        return releaseEntityService.findById(id).map(release -> {
            releaseEntityService.delete(release);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Release with id:" + id + Messages.MSG_NOTFOUND));
    }
}

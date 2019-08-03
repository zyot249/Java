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

@RestController
public class ReleaseController {
    @Autowired
    private ReleaseEntityService releaseEntityService;

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
        }).orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.MSG_RELEASE_NOT_FOUND_FORMAT, id)));
    }

    @DeleteMapping("/release/{id}")
    public ResponseEntity deleteRelease(@PathVariable("id") String id) {
        return releaseEntityService.findById(id).map(release -> {
            releaseEntityService.delete(release);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.MSG_RELEASE_NOT_FOUND_FORMAT, id)));
    }
}

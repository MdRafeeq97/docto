package com.app.docto.controller;

import com.app.docto.beans.HealthTag;
import com.app.docto.services.HealthTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/healthtag")
public class HealthTagController {
    @Autowired
    private HealthTagService healthTagService;

    @PostMapping
    public ResponseEntity<?> createHealthTag(@RequestBody @Valid HealthTag healthTag) {
        this.healthTagService.createHealthTag(healthTag);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<HealthTag> getHealthTag(@PathVariable(name = "id") Long healthTagId) {
        return ResponseEntity.ok().body(this.healthTagService.getHealthTag(healthTagId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<HealthTag>> getHealthTags() {
        return ResponseEntity.ok().body(this.healthTagService.getHealthTags());
    }
}

package com.app.docto.controller;

import com.app.docto.beans.Speciality;
import com.app.docto.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {
    @Autowired
    private SpecialityService specialityService;

    @PostMapping
    public ResponseEntity<?> createSpeciality(@RequestBody @Valid Speciality speciality) {
        this.specialityService.createSpeciality(speciality);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Object> updateSpeciality(@RequestBody @Valid Speciality speciality) {
        this.specialityService.updateSpeciality(speciality);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Speciality> getSpeciality(@PathVariable(name = "id") Long specialityId) {
        return ResponseEntity.ok().body(this.specialityService.getSpeciality(specialityId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Speciality>> getSpecialities() {
        return ResponseEntity.ok().body(this.specialityService.getSpecialities());
    }
}

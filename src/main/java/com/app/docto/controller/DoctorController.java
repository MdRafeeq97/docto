package com.app.docto.controller;

import com.app.docto.beans.Doctor;
import com.app.docto.beans.Slot;
import com.app.docto.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<Object> createDoctor(@RequestBody @Valid Doctor doctor) {
        this.doctorService.createDoctor(doctor);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Object> updateDoctor(@RequestBody @Valid Doctor doctor) {
        this.doctorService.updateDoctor(doctor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable(name = "id") Long doctorId) {
        return ResponseEntity.ok().body(this.doctorService.getDoctor(doctorId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Doctor>> getDoctors() {
        return ResponseEntity.ok().body(this.doctorService.getDoctors());
    }

    @PostMapping("/addSlot/{id}")
    public ResponseEntity<Object> addSlot(@PathVariable(name = "id") Long doctorId, @Valid @RequestBody Slot slot) {
        this.doctorService.addSlot(doctorId, slot);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/slots/{id}")
    public ResponseEntity<Object> getAvailableSlots(@PathVariable(name = "id") Long doctorId) {
        return ResponseEntity.ok().body(this.doctorService.getAvailableSlots(doctorId));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchDoctors(@RequestParam String query) {
        return ResponseEntity.ok().body(this.doctorService.search(query));
    }

}

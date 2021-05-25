package com.app.docto.controller;

import com.app.docto.beans.Appointment;
import com.app.docto.beans.Patient;
import com.app.docto.models.request.AppointmentRequest;
import com.app.docto.models.request.DoctorReviewReq;
import com.app.docto.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody @Valid Patient patient) {
        this.patientService.createPatient(patient);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable(name = "id") Long patientId) {
        return ResponseEntity.ok().body(this.patientService.getPatient(patientId));
    }

    @PostMapping("/{id}/appointment")
    public ResponseEntity<Object> createAppointment(@PathVariable(name = "id") Long patientId,
                                                    @Valid @RequestBody AppointmentRequest appointmentRequest) {
        this.patientService.createAppointment(patientId, appointmentRequest);
        return ResponseEntity.ok().body("Appointment Successfully booked");
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Object> reviewDoctor(@PathVariable(name = "id") Long patientId,
                                               @RequestBody @Valid DoctorReviewReq doctorReviewReq) {
        this.patientService.reviewDoctor(patientId, doctorReviewReq);
        return ResponseEntity.ok().body("Review Successfully added");
    }
}

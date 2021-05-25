package com.app.docto.services.implementation;

import com.app.docto.beans.Appointment;
import com.app.docto.beans.Doctor;
import com.app.docto.beans.DoctorReview;
import com.app.docto.beans.Patient;
import com.app.docto.dao.AppointmentRepository;
import com.app.docto.dao.DoctorRepository;
import com.app.docto.dao.PatientRepository;
import com.app.docto.enums.AppointmentStatus;
import com.app.docto.exception.ValidationException;
import com.app.docto.mapper.AppointmentRequestMapper;
import com.app.docto.models.request.AppointmentRequest;
import com.app.docto.models.request.DoctorReviewReq;
import com.app.docto.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRequestMapper mapper;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void createPatient(Patient patient) {
        this.patientRepository.save(patient);
    }

    @Override
    public Patient getPatient(Long patientId) {
        return this.patientRepository.findById(patientId)
                .orElseThrow(() -> new ValidationException("Patient doesn't exists"));
    }

    @Override
    public void createAppointment(Long patientId, AppointmentRequest appointmentRequest) {
        this.doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new ValidationException("Doctor doesn't exists"));

        Patient patient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new ValidationException("Patient doesn't exists "));

        Appointment appointment = this.mapper.mapOne(appointmentRequest);
        appointment.setPatient(patient);
        List<Appointment> appointments = patient.getAppointments();
        appointments.add(appointment);
        this.patientRepository.save(patient);
    }

    @Override
    public void reviewDoctor(Long patientId, DoctorReviewReq reviewReq) {
        Patient patient = this.patientRepository.findById(patientId)
                .orElseThrow(() -> new ValidationException("Patient doesn't exists "));

        Appointment appointment = this.appointmentRepository.findByPatientPatientidAndAppointmentId(patientId, reviewReq.getAppointmentId())
                .orElseThrow(() -> new ValidationException("Appointment doesn't exists"));

        if(appointment.getStatus() != AppointmentStatus.CLOSED) {
            throw new ValidationException("Cannot review for an unsuccessful appointment");
        }

//        if(appointment.getAppointmentDate().after(new Date())) {
//            throw new ValidationException("Cannot review before the appointment is closed");
//        }

        Doctor doctor = appointment.getDoctorSlot().getDoctor();
        DoctorReview doctorReview = new DoctorReview();
        doctorReview.setDoctor(doctor);
        doctorReview.setPatient(patient);
        doctorReview.setStars(reviewReq.getStars());
        List<DoctorReview> reviews = doctor.getDoctorReviews();
        reviews.add(doctorReview);
        doctor.setDoctorReviews(reviews);
        this.doctorRepository.save(doctor);
    }
}

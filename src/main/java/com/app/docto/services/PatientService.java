package com.app.docto.services;

import com.app.docto.beans.Patient;
import com.app.docto.models.request.AppointmentRequest;
import com.app.docto.models.request.DoctorReviewReq;

public interface PatientService  {
    void createPatient(Patient patient);

    Patient getPatient(Long patientId);

    void createAppointment(Long patientId, AppointmentRequest appointment);

    void reviewDoctor(Long patientId, DoctorReviewReq reviewReq);

    void cancelAppointment(Long patientId, Long appointmentId);
}

package com.app.docto.dao;

import com.app.docto.beans.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> { ;
    Optional<Appointment> findByPatientPatientidAndAppointmentId(Long patientId, Long appointmentId);
}

package com.app.docto.services;

import com.app.docto.beans.Doctor;
import com.app.docto.beans.DoctorSlot;
import com.app.docto.beans.Slot;
import com.app.docto.models.response.DoctorResp;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DoctorService {
    void createDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
    Doctor getDoctor(Long doctorId);
    List<Doctor> getDoctors();
    void addSlot(Long doctorId, Slot slot);
    List<Doctor> search(String query);
    List<DoctorSlot> getAvailableSlots(Long doctorId);
}

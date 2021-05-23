package com.app.docto.services;

import com.app.docto.beans.Doctor;
import com.app.docto.beans.Slot;

import java.util.List;

public interface DoctorService {
    void createDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
    Doctor getDoctor(Long doctorId);
    List<Doctor> getDoctors();
    void addSlot(Long doctorId, Slot slot);
}

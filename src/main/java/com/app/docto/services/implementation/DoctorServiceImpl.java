package com.app.docto.services.implementation;

import com.app.docto.beans.Doctor;
import com.app.docto.beans.DoctorSlot;
import com.app.docto.beans.Slot;
import com.app.docto.dao.DoctorRepository;
import com.app.docto.dao.DoctorSlotRepository;
import com.app.docto.exception.ValidationException;
import com.app.docto.services.DoctorService;
import com.app.docto.services.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SlotService slotService;

    @Autowired
    private DoctorSlotRepository doctorSlotRepository;

    @Override
    public void createDoctor(Doctor doctor) {
        this.doctorRepository.save(doctor);
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        this.doctorRepository.findById(doctor.getDoctorId())
                .orElseThrow(() -> new ValidationException("Doctor doesn't exists"));
        this.doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctor(Long doctorId) {
        return this.doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ValidationException("Doctor doesn't exists"));
    }

    @Override
    public List<Doctor> getDoctors() {
        return this.doctorRepository.findAll();
    }

    @Override
    public void addSlot(Long doctorId, Slot slot) {
     Doctor doctor = this.doctorRepository.findById(doctorId)
             .orElseThrow(() -> new ValidationException("Doctor doesn't exists"));

     if(slot.getStartTime().before(new Date())) {
         throw new ValidationException("Cannot add slot for past date");
     }
     if(slot.getEndTime().before(slot.getStartTime())) {
         throw new ValidationException("Start time should be before end time");
     }
     Slot savedSlot = this.slotService.addSlot(slot);
     DoctorSlot doctorSlot = new DoctorSlot();
     doctorSlot.setDoctor(doctor);
     doctorSlot.setSlot(savedSlot);
     doctorSlot.setAvailable(true);
     this.doctorSlotRepository.save(doctorSlot);
    }
}

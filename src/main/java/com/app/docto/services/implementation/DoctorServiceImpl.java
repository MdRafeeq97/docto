package com.app.docto.services.implementation;

import com.app.docto.beans.*;
import com.app.docto.dao.DoctorRepository;
import com.app.docto.dao.DoctorSlotRepository;
import com.app.docto.exception.ValidationException;
import com.app.docto.services.DoctorService;
import com.app.docto.services.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public
class DoctorServiceImpl implements DoctorService {
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
     this.doctorSlotRepository.findByDoctorDoctorIdAndSlotSlotId(doctorId, savedSlot.getSlotId())
            .ifPresent((doctorSlot) -> {throw new ValidationException("Slot is already added for doctor " + doctorId);});
     DoctorSlot doctorSlot = new DoctorSlot();
     doctorSlot.setDoctor(doctor);
     doctorSlot.setSlot(savedSlot);
     doctorSlot.setAvailable(true);
     this.doctorSlotRepository.save(doctorSlot);
    }

    @Override
    public List<Doctor> search(String query) {
        Specification<Doctor> specification = (root, cq, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.or(cb.like(root.get("firstName"), "%"+query+"%"), cb.like(root.get("lastName"), "%"+query+"%")));

            Join<Doctor, Speciality> doctorSpecialityJoin = root.join("specialities");
            predicates.add(cb.like(doctorSpecialityJoin.get("specialityName"), "%"+query+"%"));

            Join<Speciality, HealthTag> specialityHealthTagJoin = doctorSpecialityJoin.join("healthTags");
            predicates.add(cb.like(specialityHealthTagJoin.get("tagName"), "%"+query+"%"));
            cq.groupBy(root.get("doctorId"));
            cq.orderBy(cb.desc(root.get("avgReview")), cb.asc(root.get("doctorId")));

            return cb.or(predicates.toArray(new Predicate[0]));
        };
        return this.doctorRepository.findAll(specification);
    }

    @Override
    public List<DoctorSlot> getAvailableSlots(Long doctorId) {
        return this.doctorSlotRepository.findByDoctorDoctorId(doctorId);
    }
}

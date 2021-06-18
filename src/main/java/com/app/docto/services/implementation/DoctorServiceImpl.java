package com.app.docto.services.implementation;

import com.app.docto.beans.*;
import com.app.docto.dao.DoctorRepository;
import com.app.docto.dao.DoctorSlotRepository;
import com.app.docto.exception.ValidationException;
import com.app.docto.models.request.Slot;
import com.app.docto.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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

     this.doctorSlotRepository.findByDoctorDoctorIdAndStartTimeAndEndTime(doctorId, slot.getStartTime(), slot.getEndTime())
            .ifPresent((doctorSlot) -> {throw new ValidationException("Slot already exists for doctor " + doctorId);});
     DoctorSlot doctorSlot = new DoctorSlot();
     doctorSlot.setDoctor(doctor);
     doctorSlot.setStartTime(slot.getStartTime());
     doctorSlot.setEndTime(slot.getEndTime());
     doctorSlot.setAvailable(true);
     this.doctorSlotRepository.save(doctorSlot);
    }

    @Override
    public List<Doctor> search(String query) {
        Specification<Doctor> specification = (root, cq, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.or(cb.like(root.get("firstName"), "%"+query+"%"), cb.like(root.get("lastName"), "%"+query+"%")));

            Join<Doctor, Speciality> doctorSpecialityJoin = root.join("specialities", JoinType.LEFT);
            Join<Speciality, HealthTag> specialityHealthTagJoin = doctorSpecialityJoin.join("healthTags", JoinType.LEFT);

            predicates.add(cb.like(doctorSpecialityJoin.get("specialityName"), "%"+query+"%"));
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

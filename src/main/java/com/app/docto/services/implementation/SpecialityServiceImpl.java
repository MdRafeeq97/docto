package com.app.docto.services.implementation;

import com.app.docto.beans.Speciality;
import com.app.docto.dao.SpecialityRepository;
import com.app.docto.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    @Autowired
    private SpecialityRepository specialityRepository;

    @Override
    public void createSpeciality(Speciality speciality) {
        this.specialityRepository.save(speciality);
    }

    @Override
    public void updateSpeciality(Speciality speciality) {
        this.specialityRepository.findById(speciality.getSpecialityId())
                .orElseThrow(() -> new ValidationException("Speciality doesnt exists"));
        this.specialityRepository.save(speciality);
    }

    @Override
    public Speciality getSpeciality(Long specialityId) {
        return this.specialityRepository.findById(specialityId)
                .orElseThrow(() -> new ValidationException("Speciality doesnt exists"));
    }

    @Override
    public List<Speciality> getSpecialities() {
        return this.specialityRepository.findAll();
    }
}

package com.app.docto.services;

import com.app.docto.beans.Speciality;

import java.util.List;

public interface SpecialityService {
    void createSpeciality(Speciality speciality);
    void updateSpeciality(Speciality speciality);
    Speciality getSpeciality(Long specialityId);
    List<Speciality> getSpecialities();
}

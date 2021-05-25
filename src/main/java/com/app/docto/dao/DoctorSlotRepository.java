package com.app.docto.dao;

import com.app.docto.beans.DoctorSlot;
import com.app.docto.beans.DoctorSlotId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, DoctorSlotId> {
    Optional<DoctorSlot> findByDoctorDoctorIdAndSlotSlotId(Long doctorId, Long slotId);
    List<DoctorSlot> findByDoctorDoctorId(Long doctorId);
}

package com.app.docto.dao;

import com.app.docto.beans.DoctorSlot;
import com.app.docto.beans.DoctorSlotId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.List;

public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, DoctorSlotId> {
    List<DoctorSlot> findByDoctorDoctorId(Long doctorId);

    Optional<DoctorSlot> findByDoctorDoctorIdAndStartTimeAndEndTime(Long doctorId, Date startTime, Date endTime);
}

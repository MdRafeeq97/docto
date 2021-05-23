package com.app.docto.dao;

import com.app.docto.beans.DoctorSlot;
import com.app.docto.beans.DoctorSlotId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, DoctorSlotId> {
}

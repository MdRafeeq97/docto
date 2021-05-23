package com.app.docto.dao;

import com.app.docto.beans.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<Slot> findByStartTimeAndEndTime(Date startTime, Date endTime);
}

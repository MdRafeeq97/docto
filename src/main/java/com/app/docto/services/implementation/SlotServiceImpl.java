package com.app.docto.services.implementation;

import com.app.docto.beans.Slot;
import com.app.docto.dao.SlotRepository;
import com.app.docto.services.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlotServiceImpl implements SlotService {
    @Autowired
    private SlotRepository slotRepository;

    @Override
    public Slot addSlot(Slot slot) {
        Optional<Slot> existingSlot = this.slotRepository
                .findByStartTimeAndEndTime(slot.getStartTime(), slot.getEndTime());
        return existingSlot.orElseGet(() -> this.slotRepository.save(slot));
    }
}

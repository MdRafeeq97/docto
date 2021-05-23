package com.app.docto.beans;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "slot")
@Data
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slotid")
    private Long slotId;

    @NotNull(message = "start time cannot be empty")
    @Column(name = "starttime")
    private Date startTime;

    @NotNull(message = "endtime cannot be empty")
    @Column(name = "endtime")
    private Date endTime;

    @OneToMany(mappedBy = "slot")
    private List<DoctorSlot> doctorSlots;
}

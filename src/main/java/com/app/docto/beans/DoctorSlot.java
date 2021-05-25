package com.app.docto.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "doctor_slot")
public class DoctorSlot implements Serializable {
//    @EmbeddedId
//    private DoctorSlotId doctorSlotId = new DoctorSlotId();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorslotid")
    private Long doctorSlotId;

    @JsonIgnore
    @ManyToOne
//    @MapsId("doctorId")
    @JoinColumn(name = "doctorid", referencedColumnName = "doctorid")
    private Doctor doctor;

    @ManyToOne
//    @MapsId("slotId")
    @JoinColumn(name = "slotid", referencedColumnName = "slotid")
    private Slot slot;

    @Column(name = "available")
    private Boolean available;

}

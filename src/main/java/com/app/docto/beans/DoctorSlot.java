package com.app.docto.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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

    @NotNull(message = "start time cannot be empty")
    @Column(name = "starttime")
    private Date startTime;

    @NotNull(message = "endtime cannot be empty")
    @Column(name = "endtime")
    private Date endTime;

    @Column(name = "available")
    private Boolean available;

}

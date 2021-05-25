package com.app.docto.beans;

import com.app.docto.enums.AppointmentStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentid")
    private Long appointmentId;

    @Column(name = "appointmentdate")
    private Date appointmentDate;

    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;

    @Valid
    @OneToOne
    @JoinColumn(name = "doctorslotid")
    private DoctorSlot doctorSlot;

    @Column(name = "cancelled")
    private Boolean cancelled;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status;
}

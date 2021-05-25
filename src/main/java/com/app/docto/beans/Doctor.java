package com.app.docto.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorid")
    private Long doctorId;

    @NotEmpty(message = "provide firstName")
    @Column(name = "firstname")
    private String firstName;

    @NotEmpty(message = "provide latName")
    @Column(name = "lastname")
    private String lastName;

    @Column(name = "dateofbirth")
    private Date dateOfBirth;

    @Email(message = "provide a valid email")
    @Column(name = "email")
    private String email;

    @NotNull(message = "provide the year of experience")
    @Column(name = "experience")
    private Integer experience;

    @NotNull(message = "provide your current location")
    @Column(name = "latitude")
    private Long latitude;

    @NotNull(message = "provide your current location")
    @Column(name = "longitude")
    private Long longitude;

    @Valid
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "doctor_speciality",
            joinColumns = {@JoinColumn(name = "doctorid")},
            inverseJoinColumns = {@JoinColumn(name = "specialityid")}
            )
    private List<Speciality> specialities;

    @OneToMany(mappedBy = "doctor")
    private List<DoctorSlot> doctorSlots;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorReview> doctorReviews;
}

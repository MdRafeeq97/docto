package com.app.docto.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Data
@Entity
@Table(name = "doctor_review")
public class DoctorReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorreviewid")
    private Long doctorReviewId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doctorid")
    private Doctor doctor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;

    @Max(value = 5, message = "cannot be more than 5 stars")
    @Column(name = "stars")
    private Integer stars;
}

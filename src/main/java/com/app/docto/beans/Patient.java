package com.app.docto.beans;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "patient")
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientid;
}

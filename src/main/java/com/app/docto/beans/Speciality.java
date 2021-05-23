package com.app.docto.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "speciality")
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialityid")
    private Long specialityId;

    @NotNull(message = "speciality name to be provided")
    @Column(name = "specialityname")
    private String specialityName;

    @NotNull(message = "provide description")
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "specialities")
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "speciality_tags",
            joinColumns = { @JoinColumn(name = "specialityid") },
            inverseJoinColumns = { @JoinColumn(name = "tagid") })
    private List<HealthTag> healthTags;

}

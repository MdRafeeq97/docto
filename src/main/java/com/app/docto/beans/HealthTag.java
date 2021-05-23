package com.app.docto.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tag")
public class HealthTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagid")
    private Long tagId;

    @Column(name = "tagname")
    private String tagName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "healthTags")
    private List<Speciality> speciality;
}

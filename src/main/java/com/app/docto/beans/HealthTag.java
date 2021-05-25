package com.app.docto.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "tag")
public class HealthTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagid")
    private Long tagId;

    @NotNull(message = "health tag name should be specified")
    @Column(name = "tagname")
    private String tagName;

    @NotNull(message = "health issue description should be specified")
    @Column(name = "tagdescription")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "healthTags")
    private List<Speciality> speciality;
}

package com.lead.consult.interview.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type", nullable = false)
    private CourseType type;

    @Column(name = "name", nullable = false)
    private String name;

    public Course(CourseType type, String name) {
        this.type = type;
        this.name = name;
    }
}

package com.lead.consult.interview.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class Course {

    private CourseType type;

    @Column(name = "name", nullable = false)
    private String name;

    public Course(CourseType type, String name) {
        this.type = type;
        this.name = name;
    }

}

package com.lead.consult.interview.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Student is on of the main entity we'll be using to manipulate through the application.
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {

    public Student(String name, int age, String group, int grade) {
        super(name, age, group);
        this.grade = grade;
        this.courses = new ArrayList<>();
    }

    @Column(name = "grade", nullable = false)
    private int grade;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;

}

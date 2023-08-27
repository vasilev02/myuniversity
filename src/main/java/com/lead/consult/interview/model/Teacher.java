package com.lead.consult.interview.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Teacher extends Person {

    public Teacher(String name, int age, String group, double salary) {
        super(name, age, group);
        this.salary = salary;
        this.courses = new ArrayList<>();
    }

    @Column(name = "salary", nullable = false)
    private double salary;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;

}

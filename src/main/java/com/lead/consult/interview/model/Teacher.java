package com.lead.consult.interview.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Teacher extends Person{

    public Teacher(String name, int age, String group) {
        super(name, age, group);
        this.courses = new ArrayList<>();
    }

    @Column(name = "salary", nullable = false)
    private double salary;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;

    public void addCourse(Course course){
        this.courses.add(course);
    }

    public void removeCourse(Course course){
        this.courses.remove(course);
    }



}

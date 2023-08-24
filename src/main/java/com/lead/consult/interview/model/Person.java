package com.lead.consult.interview.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public abstract class Person {

    protected Person(String name, int age, String group) {
        this.name = name;
        this.age = age;
        this.group = group;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private final String name;

    @Column(name = "age", nullable = false)
    private final int age;

    @Column(name = "name", nullable = false)
    private final String group;
}

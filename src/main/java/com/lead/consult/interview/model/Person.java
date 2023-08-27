package com.lead.consult.interview.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Abstract class which provide all shared properties to the children.
 */
@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class Person {

    protected Person(String name, int age, String group) {
        this.name = name;
        this.age = age;
        this.groupName = group;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "age", nullable = false)
    protected int age;

    @Column(name = "groupName", nullable = false)
    protected String groupName;
}

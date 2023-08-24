package com.lead.consult.interview.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseType {
    MAIN("Main"), SECONDARY("Secondary");

    private final String value;
}

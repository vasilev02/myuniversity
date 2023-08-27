package com.lead.consult.interview.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * There are two types MAIN and SECONDARY.
 */
@Getter
@AllArgsConstructor
public enum CourseType {
    MAIN("Main"), SECONDARY("Secondary");

    private final String value;
}

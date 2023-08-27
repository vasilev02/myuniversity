package com.lead.consult.interview.messages;

public class Message {

    private Message() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATION_OF_DUPLICATE = "You are trying to create course with same type and name!";
    public static final String NO_COURSE_IN_DATABASE = "There is no course in the database with that ID - ";
    public static final String NO_STUDENT_IN_DATABASE = "There is no student in the database with that ID - ";
    public static final String ADDING_NO_EXISTING_COURSE_TO_STUDENT = "Course that you are trying to add to student does not exist!";
    public static final String ADDING_EXISTING_COURSE_TO_STUDENT = "Course that you are trying to add to student is already exist!";
    public static final String REMOVING_EXISTING_COURSE_TO_STUDENT = "Course that you are trying to remove from student does not exist!";
    public static final String NO_TEACHER_IN_DATABASE = "There is no teacher in the database with that ID - ";
    public static final String ADDING_EXISTING_COURSE_TO_TEACHER = "Course that you are trying to add to teacher is already exist!";
    public static final String ADDING_NO_EXISTING_COURSE_TO_TEACHER = "Course that you are trying to add to teacher does not exist!";
    public static final String REMOVING_EXISTING_COURSE_TO_TEACHER = "Course that you are trying to remove from teacher does not exist!";
}

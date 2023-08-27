package com.lead.consult.interview.service.interfaces;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.CourseType;

import java.util.List;
import java.util.Optional;

/**
 * CourseService is interface which give us the methods which are implemented by CourseServiceImpl.
 */
public interface CourseService {

    List<Course> getAllCourses();

    Course update(Course course);

    Course createCourse(Course course);

    Optional<Course> getCourseById(int id);

    Optional<Course> deleteCourseById(int id);

    List<Course> getCoursesByType(CourseType type);

}

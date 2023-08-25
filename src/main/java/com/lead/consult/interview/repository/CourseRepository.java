package com.lead.consult.interview.repository;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findCourseByNameAndType(String name, CourseType type);

}

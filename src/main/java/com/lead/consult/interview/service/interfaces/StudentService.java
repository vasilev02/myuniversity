package com.lead.consult.interview.service.interfaces;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Student;

import java.util.List;
import java.util.Optional;

/**
 * StudentService is interface which give us the methods which are implemented by StudentServiceImpl.
 */
public interface StudentService {

    List<Student> getAllStudents();

    Student createStudent(Student student);

    Optional<Student> getStudentById(int id);

    Optional<Student> deleteStudentById(int id);

    Student updateStudentById(Student student);

    List<Student> getStudentsByCourseName(String courseName);

    List<Student> getStudentsByGroupName(String groupName);

    List<Student> getStudentsByCourseAndGroupNames(String courseName, String groupName);

    List<Student> getStudentsByCourseNameAndAge(String courseName, Integer age);

    Student addCourse(int id, Course course);

    Student removeCourse(int id, Course course);

}

package com.lead.consult.interview.service.interfaces;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(int id);

    Student deleteStudentById(int id);

    Student updateStudentById(Student student);

    List<Student> getStudentsByCourse(Course course);

}

package com.lead.consult.interview.service.interfaces;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<Teacher> getAllTeachers();

    Teacher createTeacher(Teacher teacher);

    Optional<Teacher> getTeacherById(int id);

    Optional<Teacher> deleteTeacherById(int id);

    Teacher updateTeacherById(Teacher teacher);

    List<Teacher> getTeachersByCourseName(String courseName);

    List<Teacher> getTeachersByGroupName(String groupName);

    List<Teacher> getTeachersByCourseAndGroupNames(String courseName, String groupName);

    List<Teacher> getTeachersByCourseNameAndSalary(String courseName, double salary);

    List<Teacher> getTeachersByCourseNameAndAge(String courseName, int age);

    Teacher addCourse(int id, Course course);

    Teacher removeCourse(int id, Course course);

}

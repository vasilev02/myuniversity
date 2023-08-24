package com.lead.consult.interview.service.interfaces;

import com.lead.consult.interview.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<Teacher> getAllTeachers();

    Optional<Teacher> getTeacherById(int id);

    void deleteTeacherById(int id);

    Teacher updateTeacherById(Teacher teacher);

    List<Teacher> getTeachersByCourseName(String courseName);

    List<Teacher> getTeachersByGroupName(String groupName);

    List<Teacher> getTeachersByCourseAndGroupNames(String courseName, String groupName);

    List<Teacher> getTeachersByCourseNameAndAge(String courseName, Integer age);

}

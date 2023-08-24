package com.lead.consult.interview.service.interfaces;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(int id);

    Teacher deleteTeacherById(int id);

    Teacher updateTeacherById(Teacher teacher);

    List<Teacher> getTeachersByCourse(Course course);

}

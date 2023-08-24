package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Teacher;
import com.lead.consult.interview.repository.TeacherRepository;
import com.lead.consult.interview.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository repository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return null;
    }

    @Override
    public Teacher getTeacherById(int id) {
        return null;
    }

    @Override
    public Teacher deleteTeacherById(int id) {
        return null;
    }

    @Override
    public Teacher updateTeacherById(Teacher teacher) {
        return null;
    }

    @Override
    public List<Teacher> getTeachersByCourse(Course course) {
        return null;
    }
}

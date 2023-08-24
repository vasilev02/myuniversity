package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.model.Teacher;
import com.lead.consult.interview.repository.TeacherRepository;
import com.lead.consult.interview.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Teacher> getTeacherById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public void deleteTeacherById(int id) {
        this.repository.deleteById(id);
    }

    @Override
    public Teacher updateTeacherById(Teacher teacher) {
        return this.repository.save(teacher);
    }

    @Override
    public List<Teacher> getTeachersByCourseName(String courseName) {
        return this.getAllTeachers()
                .stream()
                .filter(teacher -> teacher.getCourses().stream().anyMatch(entry -> entry.getName().equals(courseName))).toList();
    }

    @Override
    public List<Teacher> getTeachersByGroupName(String groupName) {
        return this.getAllTeachers()
                .stream()
                .filter(teacher -> teacher.getGroupName().equals(groupName)).toList();
    }

    @Override
    public List<Teacher> getTeachersByCourseAndGroupNames(String courseName, String groupName) {
        Predicate<Teacher> teacherPredicate = e -> e.getGroupName().equals(groupName) && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));

        return this.getAllTeachers().stream().filter(teacherPredicate).toList();
    }

    @Override
    public List<Teacher> getTeachersByCourseNameAndAge(String courseName, Integer age) {
        Predicate<Teacher> teacherPredicate = e -> e.getAge() <= age && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));
        return this.getAllTeachers().stream().filter(teacherPredicate).toList();
    }
}

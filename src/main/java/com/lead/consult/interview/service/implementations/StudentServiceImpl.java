package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.model.Student;
import com.lead.consult.interview.repository.StudentRepository;
import com.lead.consult.interview.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Student> getAllStudents() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public void deleteStudentById(int id) {
        this.repository.deleteById(id);
    }

    @Override
    public Student updateStudentById(Student student) {
        return this.repository.save(student);
    }

    @Override
    public List<Student> getStudentsByCourseName(String courseName) {
        return this.getAllStudents()
                .stream()
                .filter(student -> student.getCourses().stream().anyMatch(entry -> entry.getName().equals(courseName))).toList();
    }

    @Override
    public List<Student> getStudentsByGroupName(String groupName) {
        return this.getAllStudents()
                .stream()
                .filter(student -> student.getGroupName().equals(groupName)).toList();
    }

    @Override
    public List<Student> getStudentsByCourseAndGroupNames(String courseName, String groupName) {
        Predicate<Student> studentPredicate = e -> e.getGroupName().equals(groupName) && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));

        return this.getAllStudents().stream().filter(studentPredicate).toList();

    }

    @Override
    public List<Student> getStudentsByCourseNameAndAge(String courseName, Integer age) {
        Predicate<Student> studentPredicate = e -> e.getAge() <= age && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));
        return this.getAllStudents().stream().filter(studentPredicate).toList();
    }

}

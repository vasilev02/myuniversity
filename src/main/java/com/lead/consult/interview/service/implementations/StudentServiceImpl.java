package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Student;
import com.lead.consult.interview.repository.StudentRepository;
import com.lead.consult.interview.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Student> getAllStudents() {
        return null;
    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }

    @Override
    public Student deleteStudentById(int id) {
        return null;
    }

    @Override
    public Student updateStudentById(Student student) {
        return null;
    }

    @Override
    public List<Student> getStudentsByCourse(Course course) {
        return null;
    }
}

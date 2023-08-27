package com.lead.consult.interview.controller;

import com.lead.consult.interview.exception.ApiException;
import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Student;
import com.lead.consult.interview.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(this.service.getAllStudents(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(this.service.createStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        try {
            Optional<Student> student = this.service.getStudentById(id);
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id) {
        try {
            Optional<Student> student = this.service.deleteStudentById(id);
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Student student) {
        try {
            Student studentToUpdate = this.service.updateStudentById(student);
            return new ResponseEntity<>(studentToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/getByCourseName")
    public ResponseEntity<List<Student>> getByCourseName(@RequestParam String courseName) {
        List<Student> dataResult = this.service.getStudentsByCourseName(courseName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @GetMapping("/getByGroupName")
    public ResponseEntity<List<Student>> getByGroupName(@RequestParam String groupName) {
        List<Student> dataResult = this.service.getStudentsByGroupName(groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @GetMapping("/getByCourseAndGroupName")
    public ResponseEntity<List<Student>> getByCourseAndGroupNames(@RequestParam String courseName, @RequestParam String groupName) {
        List<Student> dataResult = this.service.getStudentsByCourseAndGroupNames(courseName, groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @GetMapping("/getByCourseNameAndAge")
    public ResponseEntity<List<Student>> getByCourseNameAndAge(@RequestParam String courseName, @RequestParam int age) {
        List<Student> dataResult = this.service.getStudentsByCourseNameAndAge(courseName, age);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @PostMapping("/addCourse/{id}")
    public ResponseEntity<Object> addCourseToStudent(@PathVariable int id, @RequestBody Course course) {
        try {
            Student student = this.service.addCourse(id, course);
            return new ResponseEntity<>(student, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/removeCourse/{id}")
    public ResponseEntity<Object> removeCourseFromStudent(@PathVariable int id, @RequestBody Course course) {
        try {
            Student student = this.service.removeCourse(id, course);
            return new ResponseEntity<>(student, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }
}

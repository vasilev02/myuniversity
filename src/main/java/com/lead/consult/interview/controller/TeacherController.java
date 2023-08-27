package com.lead.consult.interview.controller;

import com.lead.consult.interview.exception.ApiException;
import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Teacher;
import com.lead.consult.interview.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return new ResponseEntity<>(this.service.getAllTeachers(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        return new ResponseEntity<>(this.service.createTeacher(teacher), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        try {
            Optional<Teacher> teacher = this.service.getTeacherById(id);
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id) {
        try {
            Optional<Teacher> teacher = this.service.deleteTeacherById(id);
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Teacher teacher) {
        try {
            Teacher teacherToUpdate = this.service.updateTeacherById(teacher);
            return new ResponseEntity<>(teacherToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/getByCourseName")
    public ResponseEntity<List<Teacher>> getByCourseName(@RequestParam String courseName) {
        List<Teacher> dataResult = this.service.getTeachersByCourseName(courseName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @GetMapping("/getByGroupName")
    public ResponseEntity<List<Teacher>> getByGroupName(@RequestParam String groupName) {
        List<Teacher> dataResult = this.service.getTeachersByGroupName(groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @GetMapping("/getByCourseAndGroupName")
    public ResponseEntity<List<Teacher>> getByCourseAndGroupNames(@RequestParam String courseName, @RequestParam String groupName) {
        List<Teacher> dataResult = this.service.getTeachersByCourseAndGroupNames(courseName, groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @GetMapping("/getByCourseNameAndAge")
    public ResponseEntity<List<Teacher>> getByCourseNameAndAge(@RequestParam String courseName, @RequestParam int age) {
        List<Teacher> dataResult = this.service.getTeachersByCourseNameAndAge(courseName, age);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @GetMapping("/getByCourseNameAndSalary")
    public ResponseEntity<List<Teacher>> getByCourseNameAndSalary(@RequestParam String courseName, @RequestParam double salary) {
        List<Teacher> dataResult = this.service.getTeachersByCourseNameAndSalary(courseName, salary);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    @PostMapping("/addCourse/{id}")
    public ResponseEntity<Object> addCourseToTeacher(@PathVariable int id, @RequestBody Course course) {
        try {
            Teacher teacher = this.service.addCourse(id, course);
            return new ResponseEntity<>(teacher, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/removeCourse/{id}")
    public ResponseEntity<Object> removeCourseFromTeacher(@PathVariable int id, @RequestBody Course course) {
        try {
            Teacher teacher = this.service.removeCourse(id, course);
            return new ResponseEntity<>(teacher, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}

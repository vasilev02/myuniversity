package com.lead.consult.interview.controller;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.CourseType;
import com.lead.consult.interview.exception.ApiException;
import com.lead.consult.interview.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Course>> getCourses(){
        return new ResponseEntity<>(this.service.getAllCourses(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody Course course){
        try {
            Course courseToAdd = this.service.createCourse(course);
            return new ResponseEntity<>(courseToAdd, HttpStatus.CREATED);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id){
        try {
            Optional<Course> course = this.service.getCourseById(id);
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id){
        try {
            Optional<Course> course = this.service.deleteCourseById(id);
            return new ResponseEntity<>(course.get(),HttpStatus.OK);
        }catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Course course){
        try {
            Course courseToUpdate = this.service.update(course);
            return new ResponseEntity<>(courseToUpdate, HttpStatus.CREATED);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/filterByType")
    @ResponseBody
    public ResponseEntity<List<Course>> getAllByType(@RequestParam CourseType type){
        return new ResponseEntity<>(this.service.getCoursesByType(type), HttpStatus.OK);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}

package com.lead.consult.interview.controller;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.CourseType;
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

    @GetMapping("/filterByType")
    @ResponseBody
    public ResponseEntity<List<Course>> getAllByType(@RequestParam CourseType type){
        return new ResponseEntity<>(this.service.getCoursesByType(type), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable int id){
        Optional<Course> course = this.service.getCourseById(id);
        if (course.isPresent()){
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Course> create(@RequestBody Course course){
        return new ResponseEntity<>(this.service.createCourse(course), HttpStatus.CREATED);
    }

    /**
     *
     * @param course
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Course> update(@RequestBody Course course){
        return new ResponseEntity<>(this.service.update(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteById(@PathVariable int id){
        this.service.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

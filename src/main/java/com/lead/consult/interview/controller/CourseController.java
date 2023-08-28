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

/**
 * CourseController class is responsible for processing incoming REST API requests.
 *
 * After request being invoked, the controller methods starts to process the web
 * request by interacting with the service layer in CourseService to complete the work that needs to be done.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        this.service = service;
    }

    /**
     * The method is used to get all courses from the database.
     *
     * @return a list of courses
     */
    @GetMapping()
    public ResponseEntity<List<Course>> getCourses() {
        return new ResponseEntity<>(this.service.getAllCourses(), HttpStatus.OK);
    }

    /**
     *The method is used to create course and save it to the database with ID which is auto incremented starting with 1.
     *
     * @param course consist of type and name
     * @return course
     * @throws IllegalArgumentException when we try to create the same course
     */
    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody Course course) {
        try {
            Course courseToAdd = this.service.createCourse(course);
            return new ResponseEntity<>(courseToAdd, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to get course from the database by providing ID.
     *
     * @param id unique integer to identify the course
     * @return object either course or handled exception
     * @throws jakarta.persistence.EntityNotFoundException when course does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        try {
            Optional<Course> course = this.service.getCourseById(id);
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to delete course from the database by providing ID.
     *
     * @param id unique integer to identify the course
     * @return object either course or handled exception
     * @throws jakarta.persistence.EntityNotFoundException when course does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id) {
        try {
            Optional<Course> course = this.service.deleteCourseById(id);
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to update course from the database by providing ID.
     *
     * @param course consist of id, type and name
     * @return course
     * @throws jakarta.persistence.EntityNotFoundException when course does not exist
     * @throws IllegalArgumentException when we try to update course with identical props
     */
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Course course) {
        try {
            Course courseToUpdate = this.service.update(course);
            return new ResponseEntity<>(courseToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to get courses from DB by giving filter with type(MAIN or SECONDARY).
     *
     * @param type enum which can either MAIN or SECONDARY
     * @return list of courses
     */
    @GetMapping("/filterByType")
    @ResponseBody
    public ResponseEntity<List<Course>> getAllByType(@RequestParam CourseType type) {
        return new ResponseEntity<>(this.service.getCoursesByType(type), HttpStatus.OK);
    }

    /**
     *The method is used to build response if we get any exception throughout our logic.
     *
     * @param exception of any type
     * @return ResponseEntity of Object
     */
    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}

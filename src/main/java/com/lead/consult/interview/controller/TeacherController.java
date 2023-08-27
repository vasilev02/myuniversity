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

/**
 * TeacherController class is responsible for processing incoming REST API requests.
 *
 * After request being invoked, the controller methods starts to process the web
 * request by interacting with the service layer in TeacherService to complete the work that needs to be done.
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    /**
     * The method is used to get all teachers from the database.
     *
     * @return a list of teachers
     */
    @GetMapping()
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return new ResponseEntity<>(this.service.getAllTeachers(), HttpStatus.OK);
    }

    /**
     *The method is used to create teacher and save it to the database with ID which is auto incremented starting with 1.
     *
     * @param teacher consist of name, age, groupName, salary, courses
     * @return teacher
     */
    @PostMapping()
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        return new ResponseEntity<>(this.service.createTeacher(teacher), HttpStatus.CREATED);
    }

    /**
     *The method is used to get teacher from the database by providing ID.
     *
     * @param id unique integer to identify the student
     * @return object either teacher or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        try {
            Optional<Teacher> teacher = this.service.getTeacherById(id);
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to delete teacher from the database by providing ID.
     *
     * @param id unique integer to identify the course
     * @return object either teacher or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id) {
        try {
            Optional<Teacher> teacher = this.service.deleteTeacherById(id);
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to update teacher from the database by providing ID.
     *
     * @param teacher consist of id, name, age, groupName, salary, courses
     * @return object either teacher or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Teacher teacher) {
        try {
            Teacher teacherToUpdate = this.service.updateTeacherById(teacher);
            return new ResponseEntity<>(teacherToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to get teachers from the database by providing name of course.
     *
     * @param courseName
     * @return list of Teacher objects
     */
    @GetMapping("/getByCourseName")
    public ResponseEntity<List<Teacher>> getByCourseName(@RequestParam String courseName) {
        List<Teacher> dataResult = this.service.getTeachersByCourseName(courseName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to get teachers from the database by providing name of group.
     *
     * @param groupName
     * @return list of Teacher objects
     */
    @GetMapping("/getByGroupName")
    public ResponseEntity<List<Teacher>> getByGroupName(@RequestParam String groupName) {
        List<Teacher> dataResult = this.service.getTeachersByGroupName(groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to get teachers from the database by providing name of course and group.
     *
     * @param courseName
     * @param groupName
     * @return list of Teacher objects
     */
    @GetMapping("/getByCourseAndGroupName")
    public ResponseEntity<List<Teacher>> getByCourseAndGroupNames(@RequestParam String courseName, @RequestParam String groupName) {
        List<Teacher> dataResult = this.service.getTeachersByCourseAndGroupNames(courseName, groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to get teachers from the database by providing name of course and age.
     *
     * @param courseName
     * @param age
     * @return list of Teacher objects
     */
    @GetMapping("/getByCourseNameAndAge")
    public ResponseEntity<List<Teacher>> getByCourseNameAndAge(@RequestParam String courseName, @RequestParam int age) {
        List<Teacher> dataResult = this.service.getTeachersByCourseNameAndAge(courseName, age);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to get teachers from the database by providing name of course and salary.
     *
     * @param courseName
     * @param salary
     * @return list of Teacher objects
     */
    @GetMapping("/getByCourseNameAndSalary")
    public ResponseEntity<List<Teacher>> getByCourseNameAndSalary(@RequestParam String courseName, @RequestParam double salary) {
        List<Teacher> dataResult = this.service.getTeachersByCourseNameAndSalary(courseName, salary);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to add course to teacher.
     *
     * @param id
     * @param course
     * @return object either teacher or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @PostMapping("/addCourse/{id}")
    public ResponseEntity<Object> addCourseToTeacher(@PathVariable int id, @RequestBody Course course) {
        try {
            Teacher teacher = this.service.addCourse(id, course);
            return new ResponseEntity<>(teacher, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to remove course from teacher.
     *
     * @param id
     * @param course
     * @return object either teacher or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @PostMapping("/removeCourse/{id}")
    public ResponseEntity<Object> removeCourseFromTeacher(@PathVariable int id, @RequestBody Course course) {
        try {
            Teacher teacher = this.service.removeCourse(id, course);
            return new ResponseEntity<>(teacher, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
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

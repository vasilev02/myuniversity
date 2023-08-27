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

/**
 * StudentController class is responsible for processing incoming REST API requests.
 *
 * After request being invoked, the controller methods starts to process the web
 * request by interacting with the service layer in StudentService to complete the work that needs to be done.
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * The method is used to get all students from the database.
     *
     * @return a list of students
     */
    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(this.service.getAllStudents(), HttpStatus.OK);
    }

    /**
     *The method is used to create student and save it to the database with ID which is auto incremented starting with 1.
     *
     * @param student consist of name, age, groupName, grade, courses
     * @return student
     */
    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(this.service.createStudent(student), HttpStatus.CREATED);
    }

    /**
     *The method is used to get student from the database by providing ID.
     *
     * @param id unique integer to identify the student
     * @return object either student or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        try {
            Optional<Student> student = this.service.getStudentById(id);
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to delete student from the database by providing ID.
     *
     * @param id unique integer to identify the course
     * @return object either student or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id) {
        try {
            Optional<Student> student = this.service.deleteStudentById(id);
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to update student from the database by providing ID.
     *
     * @param student consist of id, name, age, groupName, grade, courses
     * @return object either student or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Student student) {
        try {
            Student studentToUpdate = this.service.updateStudentById(student);
            return new ResponseEntity<>(studentToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to get students from the database by providing name of course.
     *
     * @param courseName
     * @return list of Student objects
     */
    @GetMapping("/getByCourseName")
    public ResponseEntity<List<Student>> getByCourseName(@RequestParam String courseName) {
        List<Student> dataResult = this.service.getStudentsByCourseName(courseName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to get students from the database by providing name of group.
     *
     * @param groupName
     * @return list of Student objects
     */
    @GetMapping("/getByGroupName")
    public ResponseEntity<List<Student>> getByGroupName(@RequestParam String groupName) {
        List<Student> dataResult = this.service.getStudentsByGroupName(groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to get students from the database by providing name of course and group.
     *
     * @param courseName
     * @param groupName
     * @return list of Student objects
     */
    @GetMapping("/getByCourseAndGroupName")
    public ResponseEntity<List<Student>> getByCourseAndGroupNames(@RequestParam String courseName, @RequestParam String groupName) {
        List<Student> dataResult = this.service.getStudentsByCourseAndGroupNames(courseName, groupName);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to get students from the database by providing name of course and age.
     *
     * @param courseName
     * @param age
     * @return list of Student objects
     */
    @GetMapping("/getByCourseNameAndAge")
    public ResponseEntity<List<Student>> getByCourseNameAndAge(@RequestParam String courseName, @RequestParam int age) {
        List<Student> dataResult = this.service.getStudentsByCourseNameAndAge(courseName, age);
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }

    /**
     *The method is used to add course to student.
     *
     * @param id
     * @param course
     * @return object either student or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @PostMapping("/addCourse/{id}")
    public ResponseEntity<Object> addCourseToStudent(@PathVariable int id, @RequestBody Course course) {
        try {
            Student student = this.service.addCourse(id, course);
            return new ResponseEntity<>(student, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     *The method is used to remove course from student.
     *
     * @param id
     * @param course
     * @return object either student or handled exception
     * @throws jakarta.persistence.EntityNotFoundException
     */
    @PostMapping("/removeCourse/{id}")
    public ResponseEntity<Object> removeCourseFromStudent(@PathVariable int id, @RequestBody Course course) {
        try {
            Student student = this.service.removeCourse(id, course);
            return new ResponseEntity<>(student, HttpStatus.BAD_REQUEST);
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

package com.lead.consult.interview.controller;

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
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(this.service.getAllStudents(), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return new ResponseEntity<>(this.service.createStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id){
        Optional<Student> student = this.service.getStudentById(id);
        if (student.isPresent()){
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/getByCourseName")
    public ResponseEntity<List<Student>> getByCourseName(@RequestParam String courseName){
        List<Student> studentsByCourseName = this.service.getStudentsByCourseName(courseName);
        return new ResponseEntity<>(studentsByCourseName, HttpStatus.OK);
    }

    @GetMapping("/getByGroupName")
    public ResponseEntity<List<Student>> getByGroupName(@RequestParam String groupName){
        List<Student> studentsByCourseName = this.service.getStudentsByGroupName(groupName);
        return new ResponseEntity<>(studentsByCourseName, HttpStatus.OK);
    }

    @GetMapping("/getByCourseAndGroupName")
    public ResponseEntity<List<Student>> getByCourseAndGroupNames(@RequestParam String courseName,@RequestParam String groupName){
        List<Student> studentsByCourseName = this.service.getStudentsByCourseAndGroupNames(courseName,groupName);
        return new ResponseEntity<>(studentsByCourseName, HttpStatus.OK);
    }

    @GetMapping("/getByCourseNameAndAge")
    public ResponseEntity<List<Student>> getByCourseNameAndAge(@RequestParam String courseName,@RequestParam int age){
        List<Student> studentsByCourseName = this.service.getStudentsByCourseNameAndAge(courseName,age);
        return new ResponseEntity<>(studentsByCourseName, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Student> update(@RequestBody Student student){
        return new ResponseEntity<>(this.service.updateStudentById(student), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteById(@PathVariable int id){
        this.service.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

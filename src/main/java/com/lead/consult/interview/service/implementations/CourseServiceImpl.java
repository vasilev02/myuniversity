package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.messages.Message;
import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.CourseType;
import com.lead.consult.interview.repository.CourseRepository;
import com.lead.consult.interview.service.interfaces.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * CourseServiceImpl is class which implement all methods from interface CourseService.
 * Here we create the business logic regarding Course class.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    @Autowired
    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Course> getAllCourses() {
        return this.repository.findAll();
    }

    @Override
    public Course createCourse(Course course) {
        if (this.checkIfCourseTypeIsCorrect(course) && !this.checkIfCourseExist(course)) {
            return this.repository.saveAndFlush(course);
        }
        throw new IllegalArgumentException(Message.CREATION_OF_DUPLICATE);
    }

    @Override
    public Optional<Course> getCourseById(int id) {
        Optional<Course> courseFromDB = this.repository.findById(id);
        if (courseFromDB.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_COURSE_IN_DATABASE + id);
        }
        return courseFromDB;
    }

    @Override
    public Course update(Course course) {
        if (!this.checkIfCourseTypeIsCorrect(course) || this.checkIfCourseExist(course)) {
            throw new IllegalArgumentException("You are trying to update course with identical properties!");
        }
        Optional<Course> courseToUpdate = this.repository.findById(course.getId());
        if (courseToUpdate.isPresent()) {
            Course temp = courseToUpdate.get();
            temp.setType(course.getType());
            temp.setName(course.getName());
            return this.repository.saveAndFlush(temp);
        }
        throw new EntityNotFoundException(Message.NO_COURSE_IN_DATABASE + course.getId());
    }

    @Override
    public Optional<Course> deleteCourseById(int id) {
        Optional<Course> course = this.getCourseById(id);
        if (course.isPresent()) {
            this.repository.deleteById(id);
            return course;
        }
        throw new EntityNotFoundException(Message.NO_COURSE_IN_DATABASE + id);
    }

    @Override
    public List<Course> getCoursesByType(CourseType type) {
        return this.getAllCourses().stream().filter(course -> course.getType().equals(type)).toList();
    }

    private boolean checkIfCourseTypeIsCorrect(Course course) {
        if (course.getType().equals(CourseType.MAIN) || course.getType().equals(CourseType.SECONDARY)) {
            return true;
        }
        return false;
    }

    private boolean checkIfCourseExist(Course course) {
        if (this.repository.findCourseByNameAndType(course.getName(), course.getType()) != null) {
            return true;
        }
        return false;
    }

}

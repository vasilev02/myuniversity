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

    /**
     *The method is used to get all courses from the database.
     *
     * @return list of Course objects
     */
    @Override
    public List<Course> getAllCourses() {
        return this.repository.findAll();
    }

    /**
     *The method is used to create course and save it to the database with ID which is auto incremented starting with 1.
     *We validate if type is correct and if the course already exist in database.
     *
     * @param course consist of type and name
     * @return course object
     * @throws IllegalArgumentException when we try to create the same course
     */
    @Override
    public Course createCourse(Course course) {
        if (this.checkIfCourseTypeIsCorrect(course) && !this.checkIfCourseExist(course)) {
            return this.repository.saveAndFlush(course);
        }
        throw new IllegalArgumentException(Message.CREATION_OF_DUPLICATE);
    }

    /**
     *The method is used to get course from the database by providing ID.
     *We check if course with that id does not exist.
     *
     * @param id unique integer to identify the course
     * @return course object
     * @throws jakarta.persistence.EntityNotFoundException when course does not exist
     */
    @Override
    public Optional<Course> getCourseById(int id) {
        Optional<Course> courseFromDB = this.repository.findById(id);
        if (courseFromDB.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_COURSE_IN_DATABASE + id);
        }
        return courseFromDB;
    }

    /**
     *The method is used to update course from the database by providing ID.
     *We validate if type is correct and if the course already exist in database.
     *
     * @param course
     * @return course object
     * @throws jakarta.persistence.EntityNotFoundException when course does not exist
     * @throws IllegalArgumentException when we try to update course with identical props
     */
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

    /**
     *The method is used to delete course from the database by providing ID.
     *
     * @param id unique integer to identify the course
     * @return course object
     * @throws jakarta.persistence.EntityNotFoundException when course does not exist
     */
    @Override
    public Optional<Course> deleteCourseById(int id) {
        Optional<Course> course = this.getCourseById(id);
        if (course.isPresent()) {
            this.repository.deleteById(id);
            return course;
        }
        throw new EntityNotFoundException(Message.NO_COURSE_IN_DATABASE + id);
    }

    /**
     *The method is used to get courses from DB by giving filter with type(MAIN or SECONDARY).
     *
     * @param type enum which can either MAIN or SECONDARY
     * @return list of courses
     */
    @Override
    public List<Course> getCoursesByType(CourseType type) {
        return this.getAllCourses().stream().filter(course -> course.getType().equals(type)).toList();
    }

    /**
     *The method is used to check if course type is correct. It has two options either MAIN or SECONDARY.
     *
     * @param course
     * @return true or false
     */
    private boolean checkIfCourseTypeIsCorrect(Course course) {
        if (course.getType().equals(CourseType.MAIN) || course.getType().equals(CourseType.SECONDARY)) {
            return true;
        }
        return false;
    }

    /**
     *The method is used to check if course exist in the database.
     *
     * @param course
     * @return true or false
     */
    private boolean checkIfCourseExist(Course course) {
        if (this.repository.findCourseByNameAndType(course.getName(), course.getType()) != null) {
            return true;
        }
        return false;
    }

}

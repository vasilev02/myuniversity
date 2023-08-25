package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.CourseType;
import com.lead.consult.interview.repository.CourseRepository;
import com.lead.consult.interview.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Course update(Course course) {
        Optional<Course> courseToUpdate = this.repository.findById(course.getId());
        if (courseToUpdate.isPresent()) {
            Course temp = courseToUpdate.get();
            temp.setType(course.getType());
            temp.setName(course.getName());
            return this.repository.saveAndFlush(temp);
        }
        return null;
    }

    @Override
    public Course createCourse(Course course) {
        if(course.getType().equals(CourseType.MAIN) || course.getType().equals(CourseType.SECONDARY)){
            return this.repository.saveAndFlush(course);
        }
       return null;
    }

    @Override
    public Optional<Course> getCourseById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<Course> deleteCourseById(int id) {
        Optional<Course> course = this.getCourseById(id);
        if(course.isPresent()){
            this.repository.deleteById(id);
            return course;
        }
        return Optional.empty();
    }

    @Override
    public List<Course> getCoursesByType(CourseType type) {
        return this.getAllCourses().stream().filter(course -> course.getType().equals(type)).toList();
    }
}

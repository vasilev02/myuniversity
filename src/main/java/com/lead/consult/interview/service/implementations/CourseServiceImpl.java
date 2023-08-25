package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.CourseType;
import com.lead.consult.interview.repository.CourseRepository;
import com.lead.consult.interview.service.interfaces.CourseService;
import jakarta.persistence.EntityNotFoundException;
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
        Optional<Course> course1 = this.repository.findById(course.getId());
        if (course1.isPresent()) {
            Course temp = course1.get();
            temp.setType(course.getType());
            temp.setName(course.getName());
            return this.repository.saveAndFlush(temp);
        } else {
            throw new EntityNotFoundException("There is no course with ID:" + course.getId());
        }

    }

    @Override
    public Course createCourse(Course course) {
       return this.repository.saveAndFlush(course);
    }

    @Override
    public Optional<Course> getCourseById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public void deleteCourseById(int id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<Course> getCoursesByType(CourseType type) {
        return this.getAllCourses().stream().filter(course -> course.getType().equals(type)).toList();
    }
}

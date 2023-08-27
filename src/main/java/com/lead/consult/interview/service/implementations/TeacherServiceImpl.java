package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.messages.Message;
import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Teacher;
import com.lead.consult.interview.repository.CourseRepository;
import com.lead.consult.interview.repository.TeacherRepository;
import com.lead.consult.interview.service.interfaces.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final CourseRepository courseRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return this.teacherRepository.findAll();
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return this.teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Optional<Teacher> getTeacherById(int id) {
        Optional<Teacher> teacherFromDB = this.teacherRepository.findById(id);
        if (teacherFromDB.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_TEACHER_IN_DATABASE + id);
        }
        return teacherFromDB;
    }

    @Override
    public Optional<Teacher> deleteTeacherById(int id) {
        Optional<Teacher> teacher = this.getTeacherById(id);
        if (teacher.isPresent()) {
            this.teacherRepository.deleteById(id);
            return teacher;
        }
        throw new EntityNotFoundException(Message.NO_TEACHER_IN_DATABASE + id);
    }

    @Override
    public Teacher updateTeacherById(Teacher teacher) {
        Optional<Teacher> teacherFromDB = this.teacherRepository.findById(teacher.getId());
        if (teacherFromDB.isPresent()) {
            List<Course> courses = teacher.getCourses();
            if (!courses.isEmpty()) {
                for (int i = 0; i < courses.size(); i++) {
                    if (!this.checkIfCourseExist(courses.get(i))) {
                        throw new EntityNotFoundException(Message.ADDING_NO_EXISTING_COURSE_TO_TEACHER);
                    }
                }
            }
            Teacher teacherToUpdate = teacherFromDB.get();
            teacherToUpdate.setName(teacher.getName());
            teacherToUpdate.setSalary(teacher.getSalary());
            teacherToUpdate.setAge(teacher.getAge());
            teacherToUpdate.setGroupName(teacher.getGroupName());
            teacherToUpdate.setCourses(teacher.getCourses());
            return this.teacherRepository.saveAndFlush(teacherToUpdate);
        }
        throw new EntityNotFoundException(Message.NO_TEACHER_IN_DATABASE + teacher.getId());
    }

    @Override
    public List<Teacher> getTeachersByCourseName(String courseName) {
        return this.getAllTeachers()
                .stream()
                .filter(teacher -> teacher.getCourses().stream().anyMatch(entry -> entry.getName().equals(courseName))).toList();
    }

    @Override
    public List<Teacher> getTeachersByGroupName(String groupName) {
        return this.getAllTeachers()
                .stream()
                .filter(teacher -> teacher.getGroupName().equals(groupName)).toList();
    }

    @Override
    public List<Teacher> getTeachersByCourseAndGroupNames(String courseName, String groupName) {
        Predicate<Teacher> teacherPredicate = e -> e.getGroupName().equals(groupName) && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));

        return this.getAllTeachers().stream().filter(teacherPredicate).toList();
    }

    @Override
    public List<Teacher> getTeachersByCourseNameAndSalary(String courseName, double salary) {
        Predicate<Teacher> teacherPredicate = e -> e.getSalary() == salary && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));
        return this.getAllTeachers().stream().filter(teacherPredicate).toList();
    }

    public List<Teacher> getTeachersByCourseNameAndAge(String courseName, int age) {
        Predicate<Teacher> teacherPredicate = e -> e.getAge() == age && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));
        return this.getAllTeachers().stream().filter(teacherPredicate).toList();
    }

    public Teacher addCourse(int id, Course course) {
        if (!this.checkIfCourseExist(course)) {
            throw new EntityNotFoundException(Message.ADDING_NO_EXISTING_COURSE_TO_TEACHER);
        }
        Course courseToAdd = this.courseRepository.findCourseByNameAndType(course.getName(), course.getType());

        Optional<Teacher> teacherCheck = this.getTeacherById(id);
        if (teacherCheck.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_TEACHER_IN_DATABASE + id);
        }

        Teacher teacher = this.getTeacherById(id).get();
        List<Course> courses = teacher.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            Course currentCourse = courses.get(i);
            if (currentCourse.getName().equals(course.getName()) && currentCourse.getType().equals(course.getType())) {
                throw new IllegalArgumentException(Message.ADDING_EXISTING_COURSE_TO_TEACHER);
            }
        }
        courses.add(courseToAdd);
        this.teacherRepository.saveAndFlush(teacher);

        List<Teacher> teachers = courseToAdd.getTeachers();
        teachers.add(teacher);
        courseToAdd.setTeachers(teachers);
        this.courseRepository.saveAndFlush(courseToAdd);
        return teacher;
    }

    @Override
    public Teacher removeCourse(int id, Course course) {
        if (!this.checkIfCourseExist(course)) {
            throw new EntityNotFoundException(Message.REMOVING_EXISTING_COURSE_TO_TEACHER);
        }
        Course courseToRemove = this.courseRepository.findCourseByNameAndType(course.getName(), course.getType());

        Optional<Teacher> teacherCheck = this.getTeacherById(id);
        if (teacherCheck.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_TEACHER_IN_DATABASE + id);
        }

        Teacher teacher = this.getTeacherById(id).get();
        List<Course> courses = teacher.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            Course currentCourse = courses.get(i);
            if (currentCourse.getName().equals(course.getName()) && currentCourse.getType().equals(course.getType())) {
                courses.remove(courseToRemove);
                courseToRemove.getTeachers().remove(teacher);
                courseRepository.saveAndFlush(courseToRemove);
                this.teacherRepository.saveAndFlush(teacher);
                break;
            }
        }
        return teacher;
    }

    private boolean checkIfCourseExist(Course course) {
        if (this.courseRepository.findCourseByNameAndType(course.getName(), course.getType()) == null) {
            return false;
        }
        return true;
    }
}

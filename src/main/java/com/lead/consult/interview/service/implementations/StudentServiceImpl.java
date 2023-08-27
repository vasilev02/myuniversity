package com.lead.consult.interview.service.implementations;

import com.lead.consult.interview.messages.Message;
import com.lead.consult.interview.model.Course;
import com.lead.consult.interview.model.Student;
import com.lead.consult.interview.repository.CourseRepository;
import com.lead.consult.interview.repository.StudentRepository;
import com.lead.consult.interview.service.interfaces.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student createStudent(Student student) {
        return this.studentRepository.saveAndFlush(student);
    }

    @Override
    public Optional<Student> getStudentById(int id) {
        Optional<Student> studentFromDB = this.studentRepository.findById(id);
        if (studentFromDB.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_STUDENT_IN_DATABASE + id);
        }
        return studentFromDB;
    }

    @Override
    public Optional<Student> deleteStudentById(int id) {
        Optional<Student> student = this.getStudentById(id);
        if (student.isPresent()) {
            this.studentRepository.deleteById(id);
            return student;
        }
        throw new EntityNotFoundException(Message.NO_STUDENT_IN_DATABASE + id);
    }

    @Override
    public Student updateStudentById(Student student) {
        Optional<Student> studentFromDB = this.studentRepository.findById(student.getId());
        if (studentFromDB.isPresent()) {
            List<Course> courses = student.getCourses();
            if (!courses.isEmpty()) {
                for (int i = 0; i < courses.size(); i++) {
                    if (!this.checkIfCourseExist(courses.get(i))) {
                        throw new EntityNotFoundException(Message.ADDING_NO_EXISTING_COURSE_TO_STUDENT);
                    }
                }
            }
            Student studentToUpdate = studentFromDB.get();
            studentToUpdate.setName(student.getName());
            studentToUpdate.setGrade(student.getGrade());
            studentToUpdate.setAge(student.getAge());
            studentToUpdate.setGroupName(student.getGroupName());
            studentToUpdate.setCourses(student.getCourses());
            return this.studentRepository.saveAndFlush(studentToUpdate);
        }
        throw new EntityNotFoundException(Message.NO_STUDENT_IN_DATABASE + student.getId());
    }

    @Override
    public List<Student> getStudentsByCourseName(String courseName) {
        return this.getAllStudents()
                .stream()
                .filter(student -> student.getCourses().stream().anyMatch(entry -> entry.getName().equals(courseName))).toList();
    }

    @Override
    public List<Student> getStudentsByGroupName(String groupName) {
        return this.getAllStudents()
                .stream()
                .filter(student -> student.getGroupName().equals(groupName)).toList();
    }

    @Override
    public List<Student> getStudentsByCourseAndGroupNames(String courseName, String groupName) {
        Predicate<Student> studentPredicate = e -> e.getGroupName().equals(groupName) && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));

        return this.getAllStudents().stream().filter(studentPredicate).toList();

    }

    @Override
    public List<Student> getStudentsByCourseNameAndAge(String courseName, Integer age) {
        Predicate<Student> studentPredicate = e -> e.getAge() == age && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));
        return this.getAllStudents().stream().filter(studentPredicate).toList();
    }

    @Override
    public Student addCourse(int id, Course course) {
        if (!this.checkIfCourseExist(course)) {
            throw new EntityNotFoundException(Message.ADDING_NO_EXISTING_COURSE_TO_STUDENT);
        }
        Course courseToAdd = this.courseRepository.findCourseByNameAndType(course.getName(), course.getType());

        Optional<Student> studentCheck = this.getStudentById(id);
        if (studentCheck.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_STUDENT_IN_DATABASE + id);
        }

        Student student = this.getStudentById(id).get();
        List<Course> courses = student.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            Course currentCourse = courses.get(i);
            if (currentCourse.getName().equals(course.getName()) && currentCourse.getType().equals(course.getType())) {
                throw new IllegalArgumentException(Message.ADDING_EXISTING_COURSE_TO_STUDENT);
            }
        }
        courses.add(courseToAdd);
        this.studentRepository.saveAndFlush(student);

        List<Student> students = courseToAdd.getStudents();
        students.add(student);
        courseToAdd.setStudents(students);
        this.courseRepository.saveAndFlush(courseToAdd);
        return student;
    }

    @Override
    public Student removeCourse(int id, Course course) {
        if (!this.checkIfCourseExist(course)) {
            throw new EntityNotFoundException(Message.REMOVING_EXISTING_COURSE_TO_STUDENT);
        }
        Course courseToRemove = this.courseRepository.findCourseByNameAndType(course.getName(), course.getType());

        Optional<Student> studentCheck = this.getStudentById(id);
        if (studentCheck.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_STUDENT_IN_DATABASE + id);
        }

        Student student = this.getStudentById(id).get();
        List<Course> courses = student.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            Course currentCourse = courses.get(i);
            if (currentCourse.getName().equals(course.getName()) && currentCourse.getType().equals(course.getType())) {
                courses.remove(courseToRemove);
                courseToRemove.getStudents().remove(student);
                courseRepository.saveAndFlush(courseToRemove);
                this.studentRepository.saveAndFlush(student);
                break;
            }
        }
        return student;
    }

    private boolean checkIfCourseExist(Course course) {
        if (this.courseRepository.findCourseByNameAndType(course.getName(), course.getType()) == null) {
            return false;
        }
        return true;
    }

}

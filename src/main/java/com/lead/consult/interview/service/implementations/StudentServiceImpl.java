package com.lead.consult.interview.service.implementations;

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
        return this.studentRepository.findById(id);
    }

    @Override
    public void deleteStudentById(int id) {
        this.studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudentById(Student student) {
        Optional<Student> student1 = this.studentRepository.findById(student.getId());
        if (student1.isPresent()) {
            Student temp = student1.get();
            temp.setName(student.getName());
            temp.setGrade(student.getGrade());
            temp.setAge(student.getAge());
            temp.setGroupName(student.getGroupName());
            temp.setCourses(student.getCourses());
            return this.studentRepository.saveAndFlush(temp);
        } else {
            throw new EntityNotFoundException("There is no course with ID:" + student.getId());
        }
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
        if(!this.checkIfCourseExist(course)){
            return null;
        }
        Course courseToAdd = this.courseRepository.findCourseByNameAndType(course.getName(), course.getType());

        Optional<Student> studentCheck = this.getStudentById(id);
        if(studentCheck.isEmpty()){
            return null;
        }

        Student student = this.getStudentById(id).get();
        List<Course> courses = student.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            Course currentCourse = courses.get(i);
            if(currentCourse.getName().equals(course.getName())){
                return null;
            }
        }
        courses.add(courseToAdd);
        this.studentRepository.saveAndFlush(student);
        return student;
    }

    @Override
    public Student removeCourse(int id, Course course) {
        if(!this.checkIfCourseExist(course)){
            return null;
        }
        Course courseToRemove = this.courseRepository.findCourseByNameAndType(course.getName(), course.getType());

        Optional<Student> studentCheck = this.getStudentById(id);
        if(studentCheck.isEmpty()){
            return null;
        }

        Student student = this.getStudentById(id).get();
        List<Course> courses = student.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            Course currentCourse = courses.get(i);
            if(currentCourse.getName().equals(course.getName())){
                courses.remove(courseToRemove);
                this.studentRepository.saveAndFlush(student);
                break;
            }
        }
        return student;
    }

    private boolean checkIfCourseExist(Course course){
        if(this.courseRepository.findCourseByNameAndType(course.getName(), course.getType()) == null){
            return false;
        }
        return true;
    }

}

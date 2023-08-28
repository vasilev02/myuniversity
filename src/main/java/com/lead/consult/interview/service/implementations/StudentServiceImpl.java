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

/**
 * StudentServiceImpl is class which implement all methods from interface StudentService.
 * Here we create the business logic regarding Student class.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * The method is used to get all students from the database.
     *
     * @return a list of students
     */
    @Override
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    /**
     *The method is used to create student and save it to the database with ID which is auto incremented starting with 1.
     *
     * @param student consist of name, age, groupName, grade, courses
     * @return student object
     */
    @Override
    public Student createStudent(Student student) {
        return this.studentRepository.saveAndFlush(student);
    }

    /**
     *The method is used to get student from the database by providing ID.
     *
     * @param id unique integer to identify the student
     * @return student object
     * @throws jakarta.persistence.EntityNotFoundException when student does not exist
     */
    @Override
    public Optional<Student> getStudentById(int id) {
        Optional<Student> studentFromDB = this.studentRepository.findById(id);
        if (studentFromDB.isEmpty()) {
            throw new EntityNotFoundException(Message.NO_STUDENT_IN_DATABASE + id);
        }
        return studentFromDB;
    }

    /**
     *The method is used to delete student from the database by providing ID.
     *
     * @param id unique integer to identify the student
     * @return student object
     * @throws jakarta.persistence.EntityNotFoundException when student does not exist
     */
    @Override
    public Optional<Student> deleteStudentById(int id) {
        Optional<Student> student = this.getStudentById(id);
        if (student.isPresent()) {
            this.studentRepository.deleteById(id);
            return student;
        }
        throw new EntityNotFoundException(Message.NO_STUDENT_IN_DATABASE + id);
    }

    /**
     *The method is used to update student from the database by providing ID.
     *
     * @param student consist of id, name, age, groupName, grade, courses
     * @return student object
     * @throws jakarta.persistence.EntityNotFoundException when student or course does not exist
     */
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

    /**
     *The method is used to get students from the database by providing name of course.
     *
     * @param courseName
     * @return list of Student objects
     */
    @Override
    public List<Student> getStudentsByCourseName(String courseName) {
        return this.getAllStudents()
                .stream()
                .filter(student -> student.getCourses().stream().anyMatch(entry -> entry.getName().equals(courseName))).toList();
    }

    /**
     *The method is used to get students from the database by providing name of group.
     *
     * @param groupName
     * @return list of Student objects
     */
    @Override
    public List<Student> getStudentsByGroupName(String groupName) {
        return this.getAllStudents()
                .stream()
                .filter(student -> student.getGroupName().equals(groupName)).toList();
    }

    /**
     *The method is used to get students from the database by providing name of course and group.
     *
     * @param courseName
     * @param groupName
     * @return list of Student objects
     */
    @Override
    public List<Student> getStudentsByCourseAndGroupNames(String courseName, String groupName) {
        Predicate<Student> studentPredicate = e -> e.getGroupName().equals(groupName) && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));

        return this.getAllStudents().stream().filter(studentPredicate).toList();

    }

    /**
     *The method is used to get students from the database by providing name of course and age.
     *
     * @param courseName
     * @param age
     * @return list of Student objects
     */
    @Override
    public List<Student> getStudentsByCourseNameAndAge(String courseName, Integer age) {
        Predicate<Student> studentPredicate = e -> e.getAge() == age && e.getCourses().stream().
                anyMatch(course -> course.getName().equals(courseName));
        return this.getAllStudents().stream().filter(studentPredicate).toList();
    }

    /**
     *The method is used to add course to student.
     *
     * @param id
     * @param course
     * @return student object
     * @throws jakarta.persistence.EntityNotFoundException when student or course does not exist
     */
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

    /**
     *The method is used to remove course from student.
     *
     * @param id
     * @param course
     * @return student object
     * @throws jakarta.persistence.EntityNotFoundException when student ot course does not exist
     */
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

    /**
     *The method is used to check if course exist in the database.
     *
     * @param course
     * @return true or false
     */
    private boolean checkIfCourseExist(Course course) {
        if (this.courseRepository.findCourseByNameAndType(course.getName(), course.getType()) == null) {
            return false;
        }
        return true;
    }

}

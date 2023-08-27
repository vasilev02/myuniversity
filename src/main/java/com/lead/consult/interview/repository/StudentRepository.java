package com.lead.consult.interview.repository;

import com.lead.consult.interview.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * StudentRepository is interface which can give us the ability to interact with the database.
 * It extends Jpa Repository which contains the APIs for basic CRUD operations.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}

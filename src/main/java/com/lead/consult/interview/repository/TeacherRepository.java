package com.lead.consult.interview.repository;

import com.lead.consult.interview.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TeacherRepository is interface which can give us the ability to interact with the database.
 * It extends Jpa Repository which contains the APIs for basic CRUD operations.
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}

package com.elaamiri.studentsmanager.repositories;

import com.elaamiri.studentsmanager.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findStudentsByLastNameContainsOrFirstNameContains( String keyWord, Pageable pageable);
}

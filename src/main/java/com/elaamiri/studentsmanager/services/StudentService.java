package com.elaamiri.studentsmanager.services;

import com.elaamiri.studentsmanager.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface StudentService {
    Student saveNewStudent(Student student);
    Page<Student> getStudentsList(String keyword, int page, int pageSize);
    Optional<Student> getStudentById(long id);
    void deleteStudent(long id);
    Student updateStudent(long id, Student student);


}

package com.elaamiri.studentsmanager.services;

import com.elaamiri.studentsmanager.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student saveNewStudent(Student student);
    Page<Student> getStudentsList(String keyword, int page, int pageSize);
    Student getStudentById(long id);
    Student deleteStudent(long id);
    Student updateStudent(long id, Student student);


}

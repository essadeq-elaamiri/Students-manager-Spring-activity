package com.elaamiri.studentsmanager.services;

import com.elaamiri.studentsmanager.entities.Student;
import com.elaamiri.studentsmanager.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class StudentServiceImp implements StudentService{
    private StudentRepository studentRepository; // injected by constructor

    @Override
    public Student saveNewStudent(Student student) {
        if(student == null) throw  new RuntimeException("Student object is null!");
        return studentRepository.save(student);
    }


    @Override
    public Page<Student> getStudentsList( String keyword, int pageNumber, int pageSize) {
        if(keyword == null || keyword.equals(""))
            return studentRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return studentRepository.findStudentsByLastNameContainsOrFirstNameContains(keyword, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Student getStudentById(long id) {
        return null;
    }

    @Override
    public Student deleteStudent(long id) {
        return null;
    }

    @Override
    public Student updateStudent(long id, Student student) {
        return null;
    }
}

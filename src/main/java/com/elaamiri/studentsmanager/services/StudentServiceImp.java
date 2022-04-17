package com.elaamiri.studentsmanager.services;

import com.elaamiri.studentsmanager.entities.Student;
import com.elaamiri.studentsmanager.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class StudentServiceImp implements StudentService{
    private StudentRepository studentRepository; // injected by constructor

    @Override
    public Student saveNewStudent(Student student) {
        if(student == null) throw  new RuntimeException("Invalid Student object (null)!");
        return studentRepository.save(student);
    }


    @Override
    public Page<Student> getStudentsList( String keyword, int pageNumber, int pageSize) {
        if(keyword == null || keyword.equals(""))
            return studentRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return studentRepository.findStudentsByLastNameContainsOrFirstNameContains(keyword, keyword, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Optional<Student> getStudentById(long id) {

        return Optional.of(studentRepository.getById(id));
    }

    @Override
    public void deleteStudent(long id) {
        Optional<Student> student = getStudentById(id);
        if(student.isPresent())
            studentRepository.deleteById(id);
        else
            throw new RuntimeException("Can not perform action, student does not exist!");
    }

    @Override
    public Student updateStudent(long id, Student student) {
        Optional<Student> student1 = getStudentById(id);
        if(student1.isEmpty())
            throw new RuntimeException("Can not perform action, student does not exist!");
        return saveNewStudent(student);
    }
}

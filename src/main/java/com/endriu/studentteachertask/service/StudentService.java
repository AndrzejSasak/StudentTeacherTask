package com.endriu.studentteachertask.service;

import com.endriu.studentteachertask.domain.Student;
import com.endriu.studentteachertask.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Page<Student> findStudentsWithPaginationAndSorting(int offset, int pageSisze, String field) {
        return studentRepository.findAll(PageRequest.of(offset, pageSisze)
                .withSort(Sort.by(Sort.Direction.ASC, field)));
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Student with this email already exists");
        }

        if(!isEmailValid(student.getEmail())) {
            throw new IllegalArgumentException("Email address not valid");
        }

        if(!isFirstNameValid(student.getFirstName())) {
            throw new IllegalArgumentException("First name cannot be shorter than 2 characters");
        }

        if(!isAgeValid(student.getAge())) {
            throw new IllegalArgumentException("Age has to be above 18");
        }

        studentRepository.save(student);
    }

    public void editStudentInfo(Long studentId, Student student) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if(studentOptional.isEmpty()) {
            throw new IllegalStateException("Student with this ID not found");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    private boolean isEmailValid(String email) {
        return Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches();
    }

    private boolean isFirstNameValid(String firstName) {
        return firstName.length() > 2;
    }

    private boolean isAgeValid(int age) {
        return age > 18;
    }
}

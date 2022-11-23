package com.endriu.studentteachertask.service;

import com.endriu.studentteachertask.domain.Student;
import com.endriu.studentteachertask.domain.Teacher;
import com.endriu.studentteachertask.repository.StudentRepository;
import com.endriu.studentteachertask.repository.TeacherRepository;
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
    private final TeacherRepository teacherRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Page<Student> findStudentsWithPaginationAndSorting(int offset, int pageSisze, String field) {
        return studentRepository.findAll(PageRequest.of(offset, pageSisze)
                .withSort(Sort.by(Sort.Direction.ASC, field)));
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findByEmail(student.getEmail());

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

    public void addTeacherToStudent(Long studentId, Long teacherId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

        if(studentOptional.isEmpty()) {
            //TODO: throw exception and return status code
        }

        if(teacherOptional.isEmpty()) {
            //TODO: throw exception and return status code
        }

        if(studentOptional.isPresent() && teacherOptional.isPresent()) {
            Student student = studentOptional.get();
            Teacher teacher = teacherOptional.get();

            teacher.addStudent(student);
            teacherRepository.save(teacher);
        }

    }

    public void editStudentInfo(Long studentId, Student student) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        student.setId(studentId);

        if(studentOptional.isEmpty()) {
            throw new IllegalStateException("Student with this ID not found");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> getStudentsOfTeacher(Long teacherId) {
        return studentRepository.findAllStudentsOfTeacher(teacherId);
    }

    public List<Student> getStudentsByFirstNameAndLastName(String firstName, String lastName) {
        return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public void removeTeacherOfStudent(Long studentId, Long teacherId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

        if(studentOptional.isEmpty()) {
            //TODO: throw exception and return status code
        }

        if(teacherOptional.isEmpty()) {
            //TODO: throw exception and return status code
        }

        if(studentOptional.isPresent() && teacherOptional.isPresent()) {
            Student student = studentOptional.get();
            Teacher teacher = teacherOptional.get();

            teacher.removeStudent(student);
            teacherRepository.save(teacher);
        }
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

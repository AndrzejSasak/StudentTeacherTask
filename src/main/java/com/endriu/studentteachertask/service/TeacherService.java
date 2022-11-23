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
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public Page<Teacher> findTeachersWithPaginationAndSorting(int offset, int pageSisze, String field) {
        return teacherRepository.findAll(PageRequest.of(offset, pageSisze)
                .withSort(Sort.by(Sort.Direction.ASC, field)));
    }

    public void addNewTeacher(Teacher teacher) {
        Optional<Teacher> studentOptional = teacherRepository.findTeacherByEmail(teacher.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Teacher with this email already exists");
        }
        if(!isEmailValid(teacher.getEmail())) {
            throw new IllegalArgumentException("Email address not valid");
        }

        if(!isFirstNameValid(teacher.getFirstName())) {
            throw new IllegalArgumentException("First name cannot be shorter than 2 characters");
        }

        if(!isAgeValid(teacher.getAge())) {
            throw new IllegalArgumentException("Age has to be above 18");
        }

        teacherRepository.save(teacher);
    }

    public void addStudentToTeacher(Long teacherId, Long studentId) {
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

    public void editTeacherInfo(Long teacherId, Teacher teacher) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        teacher.setId(teacherId);

        if(teacherOptional.isEmpty()) {
            throw new IllegalStateException("Teacher with this ID not found");
        }

        teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
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

    public List<Teacher> getTeachersOfStudent(Long studentId) {
        return teacherRepository.findAllTeachersOfStudent(studentId);
    }

    public List<Teacher> getStudentsByFirstNameAndLastName(String firstName, String lastName) {
        return teacherRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }
}

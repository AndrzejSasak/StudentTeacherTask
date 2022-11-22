package com.endriu.studentteachertask.service;

import com.endriu.studentteachertask.domain.Teacher;
import com.endriu.studentteachertask.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void addNewTeacher(Teacher teacher) {
        Optional<Teacher> studentOptional = teacherRepository.findTeacherByEmail(teacher.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Student with this email already exists");
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

    public void editTeacherInfo(Long teacherId, Teacher teacher) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

        if(teacherOptional.isEmpty()) {
            throw new IllegalStateException("Student with this ID not found");
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

}

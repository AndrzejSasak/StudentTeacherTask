package com.endriu.studentteachertask.service;

import com.endriu.studentteachertask.domain.Student;
import com.endriu.studentteachertask.domain.Teacher;
import com.endriu.studentteachertask.repository.StudentRepository;
import com.endriu.studentteachertask.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService extends GenericService<Teacher> {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        super(teacherRepository);
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<Teacher> getTeachersOfStudent(Long studentId) {
        return teacherRepository.findAllTeachersOfStudent(studentId);
    }

    public void addStudentToTeacher(Long teacherId, Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

        if(studentOptional.isEmpty()) {
            throw new IllegalArgumentException("Student with this ID not found");
        }

        if(teacherOptional.isEmpty()) {
            throw new IllegalArgumentException("Teacher with this ID not found");
        }

        Teacher teacher = teacherOptional.get();
        teacher.addStudent(studentOptional.get());
        teacherRepository.save(teacher);
    }

    public void removeStudentOfTeacher(Long teacherId, Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

        if(studentOptional.isEmpty()) {
            throw new IllegalArgumentException("Student with this ID not found");
        }

        if(teacherOptional.isEmpty()) {
            throw new IllegalArgumentException("Teacher with this ID not found");
        }

        Teacher teacher = teacherOptional.get();
        teacher.addStudent(studentOptional.get());
        teacherRepository.delete(teacher);
    }

}

package com.endriu.studentteachertask.service;

import com.endriu.studentteachertask.domain.Student;
import com.endriu.studentteachertask.domain.Teacher;
import com.endriu.studentteachertask.repository.StudentRepository;
import com.endriu.studentteachertask.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends GenericService<Student> {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Student> getStudentsOfTeacher(Long teacherId) {
        return studentRepository.findAllStudentsOfTeacher(teacherId);
    }

    public void addTeacherToStudent(Long studentId, Long teacherId) {
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

    public void removeTeacherOfStudent(Long studentId, Long teacherId) {
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

package com.endriu.studentteachertask.controller;

import com.endriu.studentteachertask.domain.Student;
import com.endriu.studentteachertask.repository.StudentRepository;
import com.endriu.studentteachertask.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController extends GenericController<Student> {

    private final StudentService studentService;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        super(studentRepository);
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudentsOfTeacher(@RequestParam Long teacherId) {
        return studentService.getStudentsOfTeacher(teacherId);
    }

    @PostMapping("/{studentId}/{teacherId}")
    public ResponseEntity<Student> assignTeacherToStudent(@PathVariable Long studentId, @PathVariable Long teacherId) {
        try {
            studentService.addTeacherToStudent(studentId, teacherId);
        } catch(IllegalArgumentException illegalArgumentException) {
            System.out.println(illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}/{teacherId}")
    public ResponseEntity<Student> removeTeacherOfStudent(@PathVariable Long studentId, @PathVariable Long teacherId) {
        try {
            studentService.removeTeacherOfStudent(studentId, teacherId);
        } catch(IllegalArgumentException illegalArgumentException) {
            System.out.println(illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

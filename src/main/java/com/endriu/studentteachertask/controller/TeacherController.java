package com.endriu.studentteachertask.controller;

import com.endriu.studentteachertask.domain.Teacher;
import com.endriu.studentteachertask.repository.TeacherRepository;
import com.endriu.studentteachertask.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController extends GenericController<Teacher> {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService, TeacherRepository teacherRepository) {
        super(teacherRepository);
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getTeachersOfStudent(@RequestParam Long studentId) {
        return teacherService.getTeachersOfStudent(studentId);
    }

    @PostMapping("/{teacherId}/{studentId}")
    public ResponseEntity<Teacher> assignStudentToTeacher(@PathVariable Long teacherId, @PathVariable Long studentId) {
        try {
            teacherService.addStudentToTeacher(teacherId, studentId);
        } catch(IllegalArgumentException illegalArgumentException) {
            LOGGER.error(illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{teacherId}/{studentId}")
    public ResponseEntity<Teacher> removeStudentOfTeacher(@PathVariable Long teacherId, @PathVariable Long studentId) {
        try {
            teacherService.removeStudentOfTeacher(teacherId, studentId);
        } catch(IllegalArgumentException illegalArgumentException) {
            LOGGER.error(illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

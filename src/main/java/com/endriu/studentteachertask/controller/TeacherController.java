package com.endriu.studentteachertask.controller;

import com.endriu.studentteachertask.domain.Teacher;
import com.endriu.studentteachertask.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping
    public void registerStudent(@RequestBody Teacher teacher) {
        teacherService.addNewTeacher(teacher);
    }

    @PutMapping("/{teacherId}")
    public void editTeacherInfo(@PathVariable Long teacherId, @RequestBody Teacher teacher) {
        teacherService.editTeacherInfo(teacherId, teacher);
    }

    @DeleteMapping("/{teacherId}")
    public void deleteTeacher(@PathVariable Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

}

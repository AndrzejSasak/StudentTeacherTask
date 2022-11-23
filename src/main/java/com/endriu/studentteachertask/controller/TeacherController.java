package com.endriu.studentteachertask.controller;

import com.endriu.studentteachertask.domain.Teacher;
import com.endriu.studentteachertask.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<Teacher> getStudentsWithSortingAndPagination(@PathVariable int offset,
                                                             @PathVariable int pageSize,
                                                             @PathVariable String field) {
        return teacherService.findTeachersWithPaginationAndSorting(offset, pageSize, field);
    }

    @GetMapping
    public List<Teacher> getTeachersOfStudent(@RequestParam Long studentId) {
        return teacherService.getTeachersOfStudent(studentId);
    }

    @GetMapping("/by-full-name")
    public List<Teacher> getTeachersByFirstNameAndLastName(@RequestParam String firstName,
                                                           @RequestParam String lastName) {
        return teacherService.getStudentsByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping
    public void registerTeacher(@RequestBody Teacher teacher) {
        teacherService.addNewTeacher(teacher);
    }

    @PostMapping("/{teacherId}/{studentId}")
    public void assignStudentToTeacher(@PathVariable Long teacherId, @PathVariable Long studentId) {
        teacherService.addStudentToTeacher(teacherId, studentId);
    }

    @PutMapping("/{teacherId}")
    public void editTeacherInfo(@PathVariable Long teacherId, @RequestBody Teacher teacher) {
        teacherService.editTeacherInfo(teacherId, teacher);
    }

    @DeleteMapping("/{teacherId}")
    public void deleteTeacher(@PathVariable Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

    @DeleteMapping("/{teacherId}/{studentId}")
    public void removeStudentOfTeacher(@PathVariable Long teacherId, @PathVariable Long studentId) {
        teacherService.removeStudentOfTeacher(teacherId, studentId);
    }

}

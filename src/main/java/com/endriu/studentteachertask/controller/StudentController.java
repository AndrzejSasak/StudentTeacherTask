package com.endriu.studentteachertask.controller;

import com.endriu.studentteachertask.domain.Student;
import com.endriu.studentteachertask.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<Student> getStudentsWithSortingAndPagination(@PathVariable int offset,
                                                             @PathVariable int pageSize,
                                                             @PathVariable String field) {
        return studentService.findStudentsWithPaginationAndSorting(offset, pageSize, field);
    }

    @GetMapping
    public List<Student> getStudentsOfTeacher(@RequestParam Long teacherId) {
        return studentService.getStudentsOfTeacher(teacherId);
    }

    @GetMapping("/by-full-name")
    public List<Student> getStudentsByFirstNameAndLastName(@RequestParam String firstName,
                                                           @RequestParam String lastName) {
        return studentService.getStudentsByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping
    public void registerStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @PostMapping("/{studentId}/{teacherId}")
    public void assignTeacherToStudent(@PathVariable Long studentId, @PathVariable Long teacherId) {
        studentService.addTeacherToStudent(studentId, teacherId);
    }

    @PutMapping("/{studentId}")
    public void editStudentInfo(@PathVariable Long studentId, @RequestBody Student student) {
        studentService.editStudentInfo(studentId, student);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @DeleteMapping("/{studentId}/{teacherId}")
    public void removeTeacherOfStudent(@PathVariable Long studentId, @PathVariable Long teacherId) {
        studentService.removeTeacherOfStudent(studentId, teacherId);
    }


}

package com.endriu.studentteachertask.controller;

import com.endriu.studentteachertask.domain.Student;
import com.endriu.studentteachertask.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{field}")
    public Page<Student> getStudentsWithSortingAndPagination(@PathVariable int offset,
                                                             @PathVariable int pageSize,
                                                             @PathVariable String field) {
        return studentService.findStudentsWithPaginationAndSorting(offset, pageSize, field);
    }

    @PostMapping
    public void registerStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @PutMapping("/{studentId}")
    public void editStudentInfo(@PathVariable Long studentId, @RequestBody Student student) {
        studentService.editStudentInfo(studentId, student);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }


}

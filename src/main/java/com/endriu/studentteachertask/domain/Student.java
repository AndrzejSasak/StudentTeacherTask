package com.endriu.studentteachertask.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "student")
@JsonIgnoreProperties("teachers")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private int age;

    @ManyToMany(mappedBy = "students")
    private List<Teacher> teachers;

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

}

package com.endriu.studentteachertask.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return age == student.age
                && Objects.equals(id, student.id)
                && Objects.equals(firstName, student.firstName)
                && Objects.equals(lastName, student.lastName)
                && Objects.equals(email, student.email)
                && Objects.equals(major, student.major)
                && Objects.equals(teachers, student.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, major, age, teachers);
    }
}

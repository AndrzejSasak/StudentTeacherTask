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
@Table(name = "teacher")
@JsonIgnoreProperties("students")
public class Teacher implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private int age;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teacher_student",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return age == teacher.age
                && Objects.equals(id, teacher.id)
                && Objects.equals(firstName, teacher.firstName)
                && Objects.equals(lastName, teacher.lastName)
                && Objects.equals(email, teacher.email)
                && Objects.equals(subject, teacher.subject)
                && Objects.equals(students, teacher.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, subject, age, students);
    }
}

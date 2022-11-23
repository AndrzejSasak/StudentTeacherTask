package com.endriu.studentteachertask.repository;

import com.endriu.studentteachertask.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    @Query("SELECT s FROM Student s JOIN s.teachers st ON st.id = :teacherId")
    List<Student> findAllStudentsOfTeacher(@Param("teacherId") Long teacherId);

    List<Student> findAllByFirstNameAndLastName(String firstName, String lastName);

}

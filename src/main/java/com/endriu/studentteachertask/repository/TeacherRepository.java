package com.endriu.studentteachertask.repository;

import com.endriu.studentteachertask.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findTeacherByEmail(String email);

    @Query("SELECT t FROM Teacher t JOIN t.students ts ON ts.id = :studentId")
    List<Teacher> findAllTeachersOfStudent(@Param("studentId") Long studentId);

    List<Teacher> findAllByFirstNameAndLastName(String firstName, String lastName);

}

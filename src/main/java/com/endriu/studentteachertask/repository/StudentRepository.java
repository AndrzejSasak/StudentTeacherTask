package com.endriu.studentteachertask.repository;

import com.endriu.studentteachertask.domain.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends GenericRepository<Student> {

    @Query("SELECT s FROM Student s JOIN s.teachers st ON st.id = :teacherId")
    List<Student> findAllStudentsOfTeacher(@Param("teacherId") Long teacherId);

}

package com.endriu.studentteachertask.repository;

import com.endriu.studentteachertask.domain.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends GenericRepository<Teacher> {

    @Query("SELECT t FROM Teacher t JOIN t.students ts ON ts.id = :studentId")
    List<Teacher> findAllTeachersOfStudent(@Param("studentId") Long studentId);

}

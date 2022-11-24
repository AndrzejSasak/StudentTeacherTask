package com.endriu.studentteachertask.repository;

import com.endriu.studentteachertask.domain.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Long> {

    Optional<T> findByEmail(String email);

    List<T> findAllByFirstNameAndLastName(String firstName, String lastName);

}

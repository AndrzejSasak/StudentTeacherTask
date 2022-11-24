package com.endriu.studentteachertask.service;

import com.endriu.studentteachertask.domain.GenericEntity;
import com.endriu.studentteachertask.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public abstract class GenericService<T extends GenericEntity> {

    private final GenericRepository<T> repository;

    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public Page<T> findEntityWithSortingAndPagination(int offset, int pageSize, String field) {
        return repository.findAll(PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.ASC, field)));
    }

    public List<T> getEntitiesByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public void addNewEntity(T entity) {
        Optional<T> entityOptional = repository.findByEmail(entity.getEmail()) ;

        if(entityOptional.isPresent()) {
            throw new IllegalArgumentException(entity.getClass() + " with this email already exists");
        }

        validateEntityData(entity);
        repository.save(entity);
    }

    public void editEntityInfo(Long entityId, T entity) {
        Optional<T> entityOptional = repository.findById(entityId);
        entity.setId(entityId);

        if(entityOptional.isEmpty()) {
            throw new IllegalArgumentException(entity.getClass() + " with this ID not found");
        }

        repository.save(entity);
    }

    public void deleteEntity(Long entityId) {
        repository.deleteById(entityId);
    }

    private void validateEntityData(T entity) {
        if(!isEmailValid(entity.getEmail())) {
            throw new IllegalArgumentException("Email address not valid");
        }

        if(!isFirstNameValid(entity.getFirstName())) {
            throw new IllegalArgumentException("First name cannot be shorter that 2 characters");
        }

        if(!isAgeValid(entity.getAge())) {
            throw new IllegalArgumentException("Age has to be above 18");
        }
    }

    private boolean isEmailValid(String email) {
        return Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches();
    }

    private boolean isFirstNameValid(String firstName) {
        return firstName.length() > 2;
    }

    private boolean isAgeValid(int age) {
        return age > 18;
    }



}

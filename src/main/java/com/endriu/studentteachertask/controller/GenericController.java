package com.endriu.studentteachertask.controller;

import com.endriu.studentteachertask.domain.GenericEntity;
import com.endriu.studentteachertask.repository.GenericRepository;
import com.endriu.studentteachertask.service.GenericService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T extends GenericEntity> {

    private final GenericService<T> service;

    protected GenericController(GenericRepository<T> repository) {
        this.service = new GenericService<>(repository) {};
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<T>> getEntitiesWithSortingAndPagination(@PathVariable int offset,
                                                       @PathVariable int pageSize,
                                                       @PathVariable String field) {
        return new ResponseEntity<>(service.findEntityWithSortingAndPagination(offset, pageSize, field), HttpStatus.OK);
    }

    @GetMapping("/by-full-name")
    public ResponseEntity<List<T>> getEntitiesByFirstNameAndLastName(@RequestParam String firstName,
                                                           @RequestParam String lastName) {
        return new ResponseEntity<>(service.getEntitiesByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<T> registerEntity(@RequestBody T entity) {
        try {
            service.addNewEntity(entity);
        } catch(IllegalArgumentException illegalArgumentException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{entityId}")
    public ResponseEntity<T> editEntityInfo(@PathVariable Long entityId, @RequestBody T entity) {
        try {
            service.editEntityInfo(entityId, entity);
        } catch(IllegalArgumentException illegalArgumentException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{entityId}")
    public ResponseEntity<T> deleteEntity(@PathVariable Long entityId) {
        try {
            service.deleteEntity(entityId);
        } catch(EmptyResultDataAccessException emptyResultDataAccessException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.models.Employee;
import com.booleanuk.api.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAll() {
        return this.departmentRepository.findAll();
    }

    @GetMapping("{id}")
    public Department getOne(@PathVariable int id) {
        return this.departmentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }

    @PostMapping
    public Department create(@RequestBody Department department) {
        return this.departmentRepository.save(department); // will probably give a 200 status response
    }

    @PutMapping("{id}")
    public Department update(@PathVariable int id, @RequestBody Department department) {
        Department departmentToUpdate = this.departmentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with given id not found"));

        departmentToUpdate.setEmployees(departmentToUpdate.getEmployees());
        departmentToUpdate.setName(departmentToUpdate.getName());

        return this.departmentRepository.save(departmentToUpdate); // will return 200 status
    }

    @DeleteMapping("{id}")
    public Department delete(@PathVariable int id) {
        Department departmentToDelete = this.departmentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        this.departmentRepository.delete(departmentToDelete);

        return departmentToDelete;
    }
}


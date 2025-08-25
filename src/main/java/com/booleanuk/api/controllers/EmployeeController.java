package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Employee;
import com.booleanuk.api.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable int id) {
        return this.employeeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee); // will probably give a 200 status response
    }

    @PutMapping("{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) {
        Employee employeeToUpdate = this.employeeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with given id not found"));

        employeeToUpdate.setFirstName(employee.getFirstName());
        employeeToUpdate.setLastName(employee.getLastName());
        employeeToUpdate.setDepartment(employee.getDepartment());

        return this.employeeRepository.save(employeeToUpdate); // will return 200 status
    }

    @DeleteMapping("{id}")
    public Employee delete(@PathVariable int id) {
        Employee employeeToDelete = this.employeeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        this.employeeRepository.delete(employeeToDelete);

        return employeeToDelete;
    }
}

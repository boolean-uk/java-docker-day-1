package com.booleanuk.api.controllers;
import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Module;
import com.booleanuk.api.repositories.CourseRepository;
import com.booleanuk.api.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/courses/{course_id}/modules")
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Module>> getModules() {
        return ResponseEntity.ok(moduleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModule(@PathVariable int id) {
        return ResponseEntity.ok(moduleRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Module not found")
                ));
    }

    @PostMapping
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        if(module.getName() == null || module.getDescription() == null || module.getCode() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Module name, description and code are required");
        }
        return ResponseEntity.ok(moduleRepository.save(module));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable int id, @RequestBody Module module) {
        Module moduleToEdit = moduleRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Module not found")
                );
        moduleToEdit.setName(module.getName());
        moduleToEdit.setDescription(module.getDescription());
        moduleToEdit.setCode(module.getCode());
        moduleRepository.save(moduleToEdit);
        return new ResponseEntity<>(moduleToEdit, HttpStatus.CREATED);
    }

    // ADD MODULE TO COURSE <--------------------------------------------------------------------------------------------------
    @PutMapping("/add-to-course/{id}")
    public ResponseEntity<Module> addModuleToCourse(@PathVariable int id, @RequestBody Course course) {
        Module module = moduleRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Module not found")
                );
        course.getModules().add(module);
        courseRepository.save(course);
        return new ResponseEntity<>(module, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Module> deleteModule(@PathVariable int id) {
        Module module = moduleRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Module not found")
                );
        // delete module from all courses that have this module
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            if (course.getModules().contains(module)) {
                course.getModules().remove(module);
                courseRepository.save(course);
            }
        }
        return ResponseEntity.ok(module);
    }
}

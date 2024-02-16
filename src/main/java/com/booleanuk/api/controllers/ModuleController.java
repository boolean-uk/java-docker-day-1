package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Module;
import com.booleanuk.api.repositories.ModuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/modules")
@AllArgsConstructor
public class ModuleController {
    private final ModuleRepository moduleRepository;

    // Get all modules
    @GetMapping
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    // Get a specific module by ID
    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable UUID id) {
        return moduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Create a new module
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Module createModule(@RequestBody Module module) {
        return moduleRepository.save(module);
    }

    // Update an existing module
    @PutMapping("/{id}")
    public Module updateModule(@PathVariable UUID id, @RequestBody Module updatedModule) {
        return moduleRepository.findById(id)
                .map(module -> {
                    module.setTitle(updatedModule.getTitle());
                    return moduleRepository.save(module);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Delete a module by ID
    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable UUID id) {
        if (moduleRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        moduleRepository.deleteById(id);
    }
}
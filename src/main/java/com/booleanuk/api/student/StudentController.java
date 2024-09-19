package com.booleanuk.api.student;

import com.booleanuk.api.Exceptions.BadRequestException;
import com.booleanuk.api.Exceptions.NotFoundException;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Student> students = studentRepository.findAll();
        Response<List<Student>> response = new Response<>(
                "success",
                students);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable int id) {
       Student student = findById(id);
        Response<Student> response = new Response<>(
                "success",
                student);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Student student) {
        try{
            this.studentRepository.save(student);
            Response<Student> response = new Response<>(
                    "success",
                    student);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable int id) {
        Student studentToUpdate = findById(id);
        try{
            studentToUpdate.setAverageGrade(student.getAverageGrade());
            studentToUpdate.setDateOfBirth(student.getDateOfBirth());
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            this.studentRepository.save(studentToUpdate);
            Response<Student> response = new Response<>(
                    "success",
                    studentToUpdate);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new BadRequestException("bad request");
        }

    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Student student = findById(id);
        this.studentRepository.delete(student);

        Response<Student> response = new Response<>(
                "success",
                student);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private Student findById(int id) {
        return this.studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );

    }
}

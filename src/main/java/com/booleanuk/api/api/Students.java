package com.booleanuk.api.api;

import com.booleanuk.api.requests.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class Students {
    private final List<Student> students = new ArrayList<>(){{
        add(new Student("Nathan", "King"));
        add(new Student("Dave", "Ames"));
    }};

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) {
        this.students.add(student);
        return student;
    }

    @GetMapping
    public List<Student> getAll() {
        return this.students;
    }

    @GetMapping("/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public Object getStudentByFirstName(@PathVariable String firstName) {
        Student student = null;
        for (Student item : this.students) {
            if (item.firstName().equals(firstName)) {
                student = item;
                break;
            }
        }

        String errorMessage = "No student with named " + firstName + " exists";
        ResponseStatusException error = new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);

        return student != null ? student : error;
    }
}

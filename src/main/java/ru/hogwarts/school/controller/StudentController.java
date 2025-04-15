package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Set;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.addStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student.getId(), student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {

        Student delStudent = null;
        try {
            delStudent = studentService.deleteStudent(id);
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (delStudent != null) {
            return ResponseEntity.ok(delStudent);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("filter/{age}")
    public ResponseEntity<Set<Student>> filterStudents(@PathVariable int age) {
        Set<Student> filterStudents = studentService.filterStudent(age);
        if (filterStudents.isEmpty()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(filterStudents);
    }

    @GetMapping("/getStudentsByAgeBetween/{min},{max}")
    public ResponseEntity<Set<Student>> getStudentsByAgeBetween(@PathVariable int min, @PathVariable int max) {
        Set<Student> getStudents = studentService.getStudentsByAgeBetween(min, max);
        if (getStudents.isEmpty()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(getStudents);
    }

    @GetMapping("/getFacultetOfStudentById/{id}")
    public ResponseEntity<Faculty> getFacultetOfStudentById(@PathVariable long id) {
        Faculty faculty = null;
        try {
            faculty = studentService.getFacultyOfStudentById(id);
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(faculty);
    }
}

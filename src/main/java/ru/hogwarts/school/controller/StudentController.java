package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("student")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
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
        Optional<Student> delStudent = studentService.deleteStudent(id);
        if (delStudent.isPresent()) {
            return ResponseEntity.ok(delStudent.get());
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
}

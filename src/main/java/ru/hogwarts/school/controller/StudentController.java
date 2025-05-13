package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("student/")
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
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {

        Student delStudent = studentService.deleteStudent(id);
        return ResponseEntity.ok(delStudent);

    }

    @GetMapping("filter/{age}")
    public ResponseEntity<Set<Student>> filterStudents(@PathVariable int age) {
        Set<Student> filterStudents = studentService.filterStudent(age);
        if (filterStudents.isEmpty()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(filterStudents);
    }

    @GetMapping("getStudentsByAgeBetween/{min},{max}")
    public ResponseEntity<Set<Student>> getStudentsByAgeBetween(@PathVariable int min, @PathVariable int max) {
        Set<Student> getStudents = studentService.getStudentsByAgeBetween(min, max);
        if (getStudents.isEmpty()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(getStudents);
    }

    @GetMapping("getFacultyOfStudentById/{id}")
    public ResponseEntity<Faculty> getFacultyOfStudentById(@PathVariable long id) {
        Faculty faculty = studentService.getFacultyOfStudentById(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("getCountStudents")
    public ResponseEntity<Integer> getCountStudents() {
        return ResponseEntity.ok(studentService.getCountStudents());
    }

    @GetMapping("getAvgAgeOfStudents")
    public ResponseEntity<Integer> getAvgAgeOfStudents() {
        return ResponseEntity.ok(studentService.getAvgAgeOfStudents());
    }

    @GetMapping("getEndFiveStudents")
    public ResponseEntity<List<Student>> getEndFiveStudents() {
        return ResponseEntity.ok(studentService.getEndFiveStudents());
    }

    @GetMapping("getStudentsWereNameStartsWithLetterA")
    public ResponseEntity<List<String>> getStudentsWereNameStartsWithLetterA() {
        return ResponseEntity.ok(studentService.getStudentsWereNameStartsWithLetterA());
    }
}

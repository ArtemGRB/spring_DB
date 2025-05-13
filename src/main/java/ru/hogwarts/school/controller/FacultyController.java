package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("faculty/")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty != null) {
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok().body(facultyService.addFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty.getId(), faculty);
        if (updateFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty delFaculty = facultyService.deleteFaculty(id);
        if (delFaculty != null) {
            return ResponseEntity.ok(delFaculty);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("filter/{color}")
    public ResponseEntity<Set<Faculty>> filterFaculties(@PathVariable String color) {
        Set<Faculty> filterFaculty = facultyService.filterFaculty(color);
        if (filterFaculty.isEmpty()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(filterFaculty);
    }

    @GetMapping("findByNameOrColor/{request}")
    public ResponseEntity<Faculty> findByNameOrColor(@PathVariable String request) {
        return ResponseEntity.ok(facultyService.getFacultyByNameOrColor(request));
    }

    @GetMapping("getStudentsOfFacultyById/{id}")
    public ResponseEntity<Collection<Student>> getStudentsOfFacultyById(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getStudentsOfFacultyById(id));
    }

    @GetMapping("getLongestName")
    public ResponseEntity<String> getLongestName() {
        return ResponseEntity.ok(facultyService.getLongestName());
    }

    @GetMapping("getInt")
    public ResponseEntity<Long> getInt() {
        return ResponseEntity.ok(facultyService.getInt());
    }
}

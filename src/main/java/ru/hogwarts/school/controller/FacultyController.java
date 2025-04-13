package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Optional<Faculty> faculty = facultyService.getFacultyById(id);
        if (faculty.isPresent()) {
            return ResponseEntity.ok(faculty.get());

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
    Optional<Faculty> delFaculty = facultyService.deleteFaculty(id);
        if (delFaculty.isPresent()) {
        return ResponseEntity.ok(delFaculty.get());
    }
        return ResponseEntity.notFound().build();
}

    @GetMapping("filter/{color}")
    public ResponseEntity<Set<Faculty>> filterStudents(@PathVariable String color) {
        Set<Faculty> filterFaculty = facultyService.filterFaculty(color);
        if (filterFaculty.isEmpty()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(filterFaculty);
    }
}

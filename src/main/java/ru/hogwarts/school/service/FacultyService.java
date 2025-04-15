package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(long id) {
        if (facultyRepository.existsById(id)){
            return facultyRepository.findById(id).get();
        }
        return null;
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        faculty.setId(id);
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        Optional<Faculty> delFaculty = facultyRepository.findById(id);
        facultyRepository.deleteById(id);
        if (facultyRepository.existsById(id)){
            facultyRepository.deleteById(id);
            return delFaculty.get();
        }
        return null;
    }

    public Set<Faculty> filterFaculty(String color) {
        return facultyRepository.findByColor(color);
    }
}

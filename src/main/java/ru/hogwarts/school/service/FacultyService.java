package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(long id) {
        logger.info("Was invoked method for get faculty by id = " + id);
        if (facultyRepository.existsById(id)) {
            return facultyRepository.findById(id).get();
        }
        return null;
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        faculty.setId(id);
        logger.info("Was invoked method for update faculty id = " + id);
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        Optional<Faculty> delFaculty = facultyRepository.findById(id);
        facultyRepository.deleteById(id);
        logger.info("Was invoked method for delete faculty id = " + id);
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            return delFaculty.get();
        }
        return null;
    }

    public Set<Faculty> filterFaculty(String color) {
        logger.info("Was invoked method for filter faculty");
        return facultyRepository.findByColor(color);
    }

    public Faculty getFacultyByNameOrColor(String request) {
        logger.info("Was invoked method for get faculty by name or color");
        return facultyRepository.findFirstByNameIgnoreCaseContainsOrColorIgnoreCaseContains(request, request);
    }

    public Collection<Student> getStudentsOfFacultyById(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow();
        logger.info("Was invoked method for get students of faculty by id = " + id);
        return faculty.getStudents();
    }
}

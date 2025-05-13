package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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

    public Faculty getFacultyByNameOrColor(String request){
            return facultyRepository.findFirstByNameIgnoreCaseContainsOrColorIgnoreCaseContains(request,request);
    }

    public Collection<Student> getStudentsOfFacultyById(long id){
        Faculty faculty = facultyRepository.findById(id).orElseThrow();
        return faculty.getStudents();
    }

    public String getLongestName() {
        List<Faculty> allFaculty = facultyRepository.findAll();
        Optional<String> longestName = allFaculty.stream()
                .map(faculty -> faculty.getName())
                .reduce((name1, name2) -> name1.length() > name2.length() ? name1 : name2);
        return longestName.get();
    }

    public long getInt() {
        long sum = LongStream
                .iterate(1, a -> a +1)
//                .parallel()
                .limit(1_000_000)
//                .parallel()
                .reduce(0,
                        (a, b) -> a + b);
        return sum;
    }
}

package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    public Student getStudentById(long id) {
        Student student = null;
        try {
            student = studentRepository.findById(id)
                    .orElseThrow(() -> new StudentNotFoundException("Студент по данному ID не найден"));
        } catch (StudentNotFoundException e) {
            logger.error("There is not student with id = " + id);
            throw new RuntimeException(e);
        }
        logger.info("Was invoked method for get student by id = " + id);
        return student;
    }

    public Student updateStudent(long id, Student student) {
        student.setId(id);
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    public Student deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент по данному ID не найден"));
        studentRepository.deleteById(id);
        logger.info("Was invoked method for delete student");
        return student;
    }

    public Set<Student> filterStudent(int age) {
        logger.info("Was invoked method for ");
        return studentRepository.findByAge(age);
    }

    public Set<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("Was invoked method for filter student");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyOfStudentById(long id) {
        logger.info("Was invoked method for get faculty of student by Id = " + id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент по данному ID не найден"))
                .getFaculty();
    }

    public int getCountStudents() {
        logger.info("Was invoked method for get count students");
        return studentRepository.getCountStudents();
    }

    public int getAvgAgeOfStudents() {

        List<Student> allStudents = studentRepository.findAll();
        double avgAge = allStudents.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
        return (int) avgAge;

    }

    public List<Student> getEndFiveStudents() {
        logger.info("Was invoked method for get end five students");
        return studentRepository.getEndFiveStudents(getCountStudents() - 5);
    }

    public List<String> getStudentsWereNameStartsWithLetterA() {
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream()
                .filter(s -> s.getName().startsWith("А"))
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

}

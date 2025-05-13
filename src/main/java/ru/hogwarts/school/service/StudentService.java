package ru.hogwarts.school.service;

import jakarta.persistence.criteria.CriteriaBuilder;
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


    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(long id) {
        Student student = null;
        try {
            student = studentRepository.findById(id)
                    .orElseThrow(()->new StudentNotFoundException("Студент по данному ID не найден"));
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public Student updateStudent(long id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    public Student deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()->new StudentNotFoundException("Студент по данному ID не найден"));
        studentRepository.deleteById(id);
            return student;
    }

    public Set<Student> filterStudent(int age) {
        return studentRepository.findByAge(age);
    }

    public Set<Student> getStudentsByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyOfStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент по данному ID не найден"))
                .getFaculty();
    }

    public int getCountStudents(){
        return studentRepository.getCountStudents();
    }

    public int getAvgAgeOfStudents(){
        List<Student> allStudents = studentRepository.findAll();
        double avgAge = allStudents.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
        return (int) avgAge;
    }


    public List<Student> getEndFiveStudents(){
        return studentRepository.getEndFiveStudents(getCountStudents()-5);
    }

    public List<String> getStudentsWereNameStartsWithLetterA(){
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream()
                .filter(s -> s.getName().startsWith("А"))
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

}

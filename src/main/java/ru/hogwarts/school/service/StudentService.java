package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Optional;
import java.util.Set;

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

    public Student deleteStudent(long id) throws StudentNotFoundException {
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

    public Faculty getFacultyOfStudentById(long id) throws StudentNotFoundException {
        return studentRepository.findById(id)
                .orElseThrow(()->new StudentNotFoundException("Студент по данному ID не найден"))
                .getFaculty();
    }
}

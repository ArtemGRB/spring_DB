package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        if (studentRepository.existsById(id)) {
            return studentRepository.findById(id).get();
        }
        return null;
    }

    public Student updateStudent(long id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    public Student deleteStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        if (studentRepository.existsById(id)) {
            return student.get();
        }
        return null;
    }

    public Set<Student> filterStudent(int age) {
        return studentRepository.findByAge(age);
    }
}

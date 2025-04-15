package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Set<Student> findByAge(int age);

    public Set<Student> findByAgeBetween(int min, int max);

    @Query("SELECT s.faculty FROM Student s WHERE s.id = :id")
    public Faculty getFacultyOfStudentById(@Param("id") long id);
}

package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Set<Student> findByAge(int age);

    Set<Student> findByAgeBetween(int min, int max);

    @Query("SELECT s.faculty FROM Student s WHERE s.id = :id")
    Faculty getFacultyOfStudentById(@Param("id") long id);

    @Query("SELECT COUNT(*) FROM Student")
    int getCountStudents();

    @Query("SELECT AVG(s.age) FROM Student s")
    int getAvgAgeOfStudents();

    @Query(value = "SELECT * FROM Student ORDER BY id LIMIT 5 OFFSET :offset",
            nativeQuery = true)
    List<Student> getEndFiveStudents(@Param("offset") int offset);
}

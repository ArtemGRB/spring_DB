package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Set<Faculty> findByColor(String color);

    Faculty findFirstByNameIgnoreCaseContainsOrColorIgnoreCaseContains(String name, String color);

}

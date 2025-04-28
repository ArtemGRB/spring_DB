package ru.hogwarts.school.controller;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyController facultyController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testGetFaculty() {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class))
                .isEqualTo("{\"id\":1,\"name\":\"Griffindor\",\"color\":\"red\"}");
    }

    @Test
    void testCreateFaculty() {

        Faculty faculty = new Faculty();
        faculty.setColor("test");
        faculty.setName("test");

        String jsonResponse = restTemplate
                .postForObject("http://localhost:" + port + "/faculty", faculty, String.class);

        Assertions.assertThat(jsonResponse).isNotNull();

        String facultyName = JsonPath.parse(jsonResponse).read("$.name", String.class);
        Assertions.assertThat(facultyName).isEqualTo("test");
        String facultyColor = JsonPath.parse(jsonResponse).read("$.color", String.class);
        Assertions.assertThat(facultyColor).isEqualTo("test");

        Long facultyId = JsonPath.parse(jsonResponse).read("$.id", Long.class);
        restTemplate.delete("http://localhost:" + port + "/faculty/" + facultyId);
    }

    @Test
    void testUpdateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setColor("color");
        faculty.setName("test");

        ResponseEntity<Faculty> response = facultyController.createFaculty(faculty);
        Long id = response.getBody().getId();
        faculty.setColor("color2");
        faculty.setName("test2");

        restTemplate.put("http://localhost:" + port + "/faculty", faculty);

        String jsonResponse = restTemplate
                .getForObject("http://localhost:" + port + "/faculty/" + id, String.class);


        Assertions.assertThat(jsonResponse).isNotNull();

        String facultyName = JsonPath.parse(jsonResponse).read("$.name", String.class);
        Assertions.assertThat(facultyName).isEqualTo("test2");
        String facultyColor = JsonPath.parse(jsonResponse).read("$.color", String.class);
        Assertions.assertThat(facultyColor).isEqualTo("color2");

        facultyController.deleteFaculty(id);
    }

    @Test
    void testDeleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setColor("color");
        faculty.setName("test");

        ResponseEntity<Faculty> response = facultyController.createFaculty(faculty);
        Long id = response.getBody().getId();


        restTemplate.delete("http://localhost:" + port + "/faculty/" + id);

        Assertions.assertThat(ResponseEntity.notFound().build())
                .isEqualTo(facultyController.deleteFaculty(id));
    }

    @Test
    void testFilterFaculty() {

        Faculty faculty = new Faculty();
        faculty.setColor("test");
        faculty.setName("test");

        ResponseEntity<Faculty> response = facultyController.createFaculty(faculty);
        Long id = response.getBody().getId();

        faculty.setId(id);

        Set<Faculty> facultySet = restTemplate
                .getForObject("http://localhost:" + port + "/faculty/filter/test", Set.class);

        facultyController.deleteFaculty(id);

        Assertions.assertThat(facultySet).isNotEmpty();

    }

    @Test
    void testFindByNameOrColor() {
        Faculty faculty = new Faculty();
        faculty.setColor("test");
        faculty.setName("test");

        ResponseEntity<Faculty> response = facultyController.createFaculty(faculty);
        Long id = response.getBody().getId();

        faculty.setId(id);

        Faculty facultyTest = restTemplate
                .getForObject("http://localhost:" + port + "/faculty/findByNameOrColor/test", Faculty.class);

        facultyController.deleteFaculty(id);

        Assertions.assertThat(facultyTest).isNotNull();

    }

    @Test
    void testGetStudentsOfFacultyById() {

        Collection<Faculty> facultyCollection = restTemplate
                .getForObject("http://localhost:" + port + "/faculty/getStudentsOfFacultyById/4"
                        , Collection.class);

        Assertions.assertThat(facultyCollection).isNotEmpty();

    }


}
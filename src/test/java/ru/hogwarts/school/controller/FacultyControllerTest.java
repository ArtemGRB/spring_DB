package ru.hogwarts.school.controller;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

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
    void createFaculty() {

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

}
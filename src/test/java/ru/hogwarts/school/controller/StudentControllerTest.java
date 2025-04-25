package ru.hogwarts.school.controller;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudent() {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/3", String.class))
                .isEqualTo("{\"id\":3,\"name\":\"Гарри Поттер\",\"age\":17}");
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setAge(99);
        student.setName("test");

        String jsonResponse = restTemplate
                .postForObject("http://localhost:" + port + "/student",student,String.class);

        Assertions.assertThat(jsonResponse).isNotNull();

        String studentName =  JsonPath.parse(jsonResponse).read("$.name", String.class);
        Assertions.assertThat(studentName).isEqualTo("test");
        Integer studentAge =  JsonPath.parse(jsonResponse).read("$.age", Integer.class);
        Assertions.assertThat(studentAge).isEqualTo(99);

        Long studentId =  JsonPath.parse(jsonResponse).read("$.id", Long.class);
        restTemplate.delete("http://localhost:" + port + "/student/" + studentId);

    }

}
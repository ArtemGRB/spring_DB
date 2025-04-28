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

import java.util.HashSet;
import java.util.Set;


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
                .postForObject("http://localhost:" + port + "/student", student, String.class);

        Assertions.assertThat(jsonResponse).isNotNull();

        String studentName = JsonPath.parse(jsonResponse).read("$.name", String.class);
        Assertions.assertThat(studentName).isEqualTo("test");
        Integer studentAge = JsonPath.parse(jsonResponse).read("$.age", Integer.class);
        Assertions.assertThat(studentAge).isEqualTo(99);

        Long studentId = JsonPath.parse(jsonResponse).read("$.id", Long.class);
        restTemplate.delete("http://localhost:" + port + "/student/" + studentId);

    }

    @Test
    void updateStudent() throws Exception {
        Student student = new Student();
        student.setAge(99);
        student.setName("test");

        ResponseEntity<Student> response = studentController.createStudent(student);
        Long id = response.getBody().getId();
        student.setAge(100);
        student.setName("test2");

        restTemplate.put("http://localhost:" + port + "/student", student);

        String jsonResponse = restTemplate
                .getForObject("http://localhost:" + port + "/student/" + id, String.class);


        Assertions.assertThat(jsonResponse).isNotNull();

        String studentName = JsonPath.parse(jsonResponse).read("$.name", String.class);
        Assertions.assertThat(studentName).isEqualTo("test2");
        Integer studentAge = JsonPath.parse(jsonResponse).read("$.age", Integer.class);
        Assertions.assertThat(studentAge).isEqualTo(100);

        studentController.deleteStudent(id);

    }

    @Test
    void deleteStudent() {

        Student student = new Student();
        student.setAge(99);
        student.setName("test");

        ResponseEntity<Student> response = studentController.createStudent(student);
        Long id = response.getBody().getId();

        restTemplate.delete("http://localhost:" + port + "/student/" + id);

        Assertions.assertThatExceptionOfType(StudentNotFoundException.class)
                .isThrownBy(() -> studentController.deleteStudent(id));
    }

    @Test
    void testFilterStudents() {
        Student student = new Student();
        student.setAge(200);
        student.setName("test");

        ResponseEntity<Student> response = studentController.createStudent(student);
        Long id = response.getBody().getId();

        student.setId(id);

        Set<Student> studentSet = restTemplate
                .getForObject("http://localhost:" + port + "/student/filter/200", Set.class);

        studentController.deleteStudent(id);

        Assertions.assertThat(studentSet).isNotEmpty();
    }

    @Test
    void getStudentsByAgeBetween() {
        Student student = new Student();
        student.setAge(200);
        student.setName("test");

        ResponseEntity<Student> response = studentController.createStudent(student);
        Long id = response.getBody().getId();

        student.setId(id);

        Set<Student> studentSet = restTemplate
                .getForObject("http://localhost:" + port + "/student/getStudentsByAgeBetween/199,201", Set.class);

        studentController.deleteStudent(id);

        Assertions.assertThat(studentSet).isNotEmpty();
    }

    @Test
    void getFacultyOfStudentById() {
        Faculty faculty = new Faculty();
        faculty.setColor("test");
        faculty.setName("test");
        faculty.setId(4L);

        Student student = new Student();
        student.setAge(200);
        student.setName("test");
        student.setFaculty(faculty);

        ResponseEntity<Student> response = studentController.createStudent(student);
        Long id = response.getBody().getId();

        student.setId(id);

        Faculty faculty1 = restTemplate
                .getForObject("http://localhost:" + port + "/student/getFacultyOfStudentById/" + id
                        , Faculty.class);

        studentController.deleteStudent(id);

        Assertions.assertThat(faculty1).isNotNull();

    }
}
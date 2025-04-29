package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;


    @Test
    void testGetStudent() throws Exception {
        long id = 1L;
        String name = "test";
        int age = 99;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));

    }

    @Test
    void testCreateStudent() throws Exception {

        long id = 1L;
        String name = "test";
        int age = 99;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void testUpdateStudent() throws Exception {
        long id = 1L;
        String name = "test";
        int age = 99;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void testDeleteStudent() throws Exception {

        long id = 1L;
        String name = "test";
        int age = 99;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void testFilterStudents() throws Exception {
        long id = 1L;
        String name = "test";
        int age = 99;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        Set<Student> studentSet = new HashSet<>();
        studentSet.add(student);

        when(studentRepository.findByAge(age)).thenReturn(studentSet);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter/" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

    }

    @Test
    void testGetStudentsByAgeBetween() throws Exception {

        long id = 1L;
        String name = "test";
        int age = 99;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        Set<Student> studentSet = new HashSet<>();
        studentSet.add(student);

        when(studentRepository.findByAgeBetween(age - 1, age + 1)).thenReturn(studentSet);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getStudentsByAgeBetween/" + (age - 1) + ","  + (age + 1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

    }

    @Test
    void testGetFacultyOfStudentById() throws Exception{

        long id = 1L;
        String name = "test";
        int age = 99;

        Faculty faculty = new Faculty();
        faculty.setId(5L);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);


        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getFacultyOfStudentById/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L));

    }
}
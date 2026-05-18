package com.bootcamp.onlineschool.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bootcamp.onlineschool.TeacherRegistry;
import com.bootcamp.onlineschool.model.Teacher;

@SpringBootTest
@DisplayName("TeacherService Tests")
public class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRegistry teacherRegistry;

    @BeforeEach
    public void setUp() {
        teacherRegistry.clear();
    }

    @Test
    @DisplayName("Should add teacher successfully")
    public void testAddTeacher() {
        Teacher teacher = new Teacher(1,"Ahmad","Ahmad@gmail.com",
        "Computer Science",5);
        teacherService.addTeacher(teacher);
        assertEquals(1, teacherService.getAllTeachers().size());
        assertEquals("Ahmad", teacher.getName());
        assertEquals("Ahmad@gmail.com", teacher.getEmail());
        assertEquals("Computer Science", teacher.getDepartment());
        assertEquals(5, teacher.getYearsOfExperience());
    }

    @Test
    @DisplayName("Should throw exception when adding null teacher")
    public void testAddNullTeacher() {
        assertThrows(IllegalArgumentException.class,
            () -> teacherService.addTeacher(null));
    }

    @Test
    @DisplayName("Should find teacher by ID")
    public void testFindTeacherById() {
        Teacher teacher = new Teacher(1,"Ahmad","Ahmad@gmail.com",
        "Computer Science",5);
        teacherService.addTeacher(teacher);
        Teacher foundTeacher = teacherService.findTeacherById(1);
        assertEquals(teacher, foundTeacher);
    }

    @Test
    @DisplayName("Should find teacher by department")
    public void testFindTeacherByDepartment() {
        Teacher teacher = new Teacher(1,"Ahmad","Ahmad@gmail.com",
        "Computer Science",5);
        teacherService.addTeacher(teacher);
        List<Teacher> foundTeachers = teacherService.findTeachersByDepartment("Computer Science");
        assertEquals(1, foundTeachers.size());
        assertEquals(teacher, foundTeachers.get(0));
    }

    @Test
    @DisplayName("Should find teacher by experience")
    public void testFindTeacherByExperience() {
        Teacher teacher = new Teacher(1,"Ahmad","Ahmad@gmail.com",
        "Computer Science",5);
        Teacher teacher2 = new Teacher(2,"Sara","Sara@gmail.com",
        "Mathematics",5);
        Teacher teacher3 = new Teacher(3,"John","John@gmail.com",
        "Physics",3);
        teacherService.addTeacher(teacher);
        teacherService.addTeacher(teacher2);
        teacherService.addTeacher(teacher3);
        List<Teacher> foundTeachersWithExperrience5 = teacherService.findTeachersByExperience(5);
        List<Teacher> foundTeachersWithExperrience3 = teacherService.findTeachersByExperience(3);
        assertEquals(1, foundTeachersWithExperrience3.size());
        assertTrue(foundTeachersWithExperrience3.contains(teacher3));
        assertFalse(foundTeachersWithExperrience3.contains(teacher));
        assertFalse(foundTeachersWithExperrience3.contains(teacher2));
        assertEquals(2, foundTeachersWithExperrience5.size());
        assertTrue(foundTeachersWithExperrience5.contains(teacher));
        assertTrue(foundTeachersWithExperrience5.contains(teacher2));
        assertFalse(foundTeachersWithExperrience5.contains(teacher3));
    }

    @Test
    @DisplayName("Should remove teacher by ID")
    public void testRemoveTeacherById() {
        Teacher teacher = new Teacher(1,"Ahmad","Ahmad@gmail.com",
        "Computer Science",5);
        teacherService.addTeacher(teacher);
        teacherService.removeTeacherById(1);
        assertEquals(0, teacherService.getAllTeachers().size());
        assertNull(teacherService.findTeacherById(1));
    }

    @Test
    @DisplayName("Should get all teachers")
    public void testGetAllTeachers() {
        Teacher teacher1 = new Teacher(1,"Ahmad","Ahmad@gmail.com",
        "Computer Science",5);
        Teacher teacher2 = new Teacher(2,"Sara","Sara@gmail.com",
        "Mathematics",5);
        teacherService.addTeacher(teacher1);
        teacherService.addTeacher(teacher2);
        assertEquals(2, teacherService.getAllTeachers().size());
    }

    @Test
    @DisplayName("Should return null when teacher not found")
    public void testFindNonExistentTeacher() {
        assertNull(teacherService.findTeacherById(999));
    }
}

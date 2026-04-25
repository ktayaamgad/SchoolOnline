package com.bootcamp.onlineschool.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teacher Class Tests")
public class TeacherTest {

    private Teacher teacher;
    private Course mathCourse;
    private Course scienceCourse;

    @BeforeEach
    public void setUp() {
        teacher = new Teacher("T001", "Dr. Smith", "smith@school.edu", "Mathematics");
        mathCourse = new Course("MATH101", "Calculus", 4);
        scienceCourse = new Course("SCI101", "Physics", 3);
    }

    @Test
    @DisplayName("should be Teacher instance")
    public void testTeacherInstance() {
        assertTrue(teacher instanceof Teacher);
    }

    @Test
    @DisplayName("Should create teacher with valid data")
    public void testTeacherCreation() {
        assertNotNull(teacher);
        assertEquals("T001", teacher.getId());
        assertEquals("Dr. Smith", teacher.getName());
        assertEquals("smith@school.edu", teacher.getEmail());
        assertEquals("Mathematics", teacher.getDepartment());
        assertTrue(teacher.getCoursesTaught().isEmpty());
    }

    @Test
    @DisplayName("Should inherit User properties and methods")
    public void testInheritance() {
        assertTrue(teacher instanceof User);

        teacher.setName("Dr. Johnson");
        assertEquals("Dr. Johnson", teacher.getName());

        teacher.setEmail("johnson@school.edu");
        assertEquals("johnson@school.edu", teacher.getEmail());

        teacher.setId("T002");
        assertEquals("T002", teacher.getId());
    }

    @Test
    @DisplayName("Should set and get department")
    public void testDepartment() {
        teacher.setDepartment("Physics");
        assertEquals("Physics", teacher.getDepartment());
    }

    @Test
    @DisplayName("Should add and remove courses")
    public void testAddRemoveCourses() {
        teacher.addCourse(mathCourse);
        assertEquals(1, teacher.getCoursesTaught().size());
        assertTrue(teacher.getCoursesTaught().contains(mathCourse));

        teacher.addCourse(scienceCourse);
        assertEquals(2, teacher.getCoursesTaught().size());

        teacher.removeCourse(mathCourse);
        assertEquals(1, teacher.getCoursesTaught().size());
        assertFalse(teacher.getCoursesTaught().contains(mathCourse));
        assertTrue(teacher.getCoursesTaught().contains(scienceCourse));
    }

    @Test
    @DisplayName("Should return correct role")
    public void testGetRole() {
        assertEquals("Teacher", teacher.getRole());
    }

    @Test
    @DisplayName("Should generate correct toString representation")
    public void testToString() {
        teacher.addCourse(mathCourse);
        String result = teacher.toString();

        assertTrue(result.contains("T001"));
        assertTrue(result.contains("Dr. Smith"));
        assertTrue(result.contains("smith@school.edu"));
        assertTrue(result.contains("Mathematics"));
        assertTrue(result.contains("MATH101"));
    }

    @Test
    @DisplayName("Should demonstrate polymorphism with User reference")
    public void testPolymorphism() {
        User user = teacher;

        assertEquals("T001", user.getId());
        assertEquals("Dr. Smith", user.getName());
        assertEquals("smith@school.edu", user.getEmail());

        String toString = user.toString();
        assertTrue(toString.startsWith("Teacher{"));
        assertTrue(toString.contains("T001"));
    }

    @Test
    @DisplayName("Should use User's equals and hashCode (not overridden)")
    public void testEqualsAndHashCode() {
        Teacher teacher2 = new Teacher("T001", "Dr. Smith", "smith@school.edu", "Mathematics");
        Teacher teacher3 = new Teacher("T002", "Dr. Smith", "smith@school.edu", "Mathematics");

        assertEquals(teacher, teacher2);
        assertNotEquals(teacher, teacher3);
        assertEquals(teacher.hashCode(), teacher2.hashCode());
    }
}

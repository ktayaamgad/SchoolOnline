package com.bootcamp.onlineschool.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Course Class Tests")
public class CourseTest {

    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course("SAEAfullstack", "how to become full-stack", 3);
    }

    @Test
    @DisplayName("Should create course and verify getters")
    public void testCourseCreation() {
        assertEquals("SAEAfullstack", course.getCourseId());
        assertEquals("how to become full-stack", course.getName());
        assertEquals(3, course.getCredits());
    }

    @Test
    @DisplayName("Should update course using setters")
    public void testSetters() {
        course.setCourseId("miao");
        course.setName("how to miao");
        course.setCredits(4);

        assertEquals("miao", course.getCourseId());
        assertEquals("how to miao", course.getName());
        assertEquals(4, course.getCredits());
    }

    @Test
    @DisplayName("Should compare courses by ID")
    public void testEqualsAndHashCode() {
        Course course1 = new Course("SAEAfullstack", "how to become full-stack", 3);
        Course course2 = new Course("miao","how to miao",4);
        
        assertEquals(course, course1);
        assertNotEquals(course, course2);
        assertEquals(course.hashCode(), course1.hashCode());
        assertNotEquals(course.hashCode(), course2.hashCode());
    }
}
package com.bootcamp.onlineschool.model;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class StudentNameComparatorTest {
    @Test
    @DisplayName("Should compare students by name")
    public void testStudentNameComparator() {
        Student student1 = new Student("id1", "Alice", "alice.smith@example.com", 38);
        Student student2 = new Student("id2", "Bob", "bob.johnson@example.com", 83);
        Student student3 = new Student("id3", "alice", "alice.smith@example.com", 38);
        Comparator<Student> comparator = new StudentNameComparator();
        assertTrue(comparator.compare(student1, student2) < 0, "Expected Alice to come before Bob");
        assertTrue(comparator.compare(student2, student1) > 0, "Expected Bob to come after Alice");
        assertTrue(comparator.compare(student1, student3) == 0, "Expected Alice to be equal to alice");
        assertTrue(comparator.compare(null, student1) > 0, "Expected null to come after Alice");
        assertTrue(comparator.compare(null, null) == 0, "Expected null to be equal to null");
    }
}

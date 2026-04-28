package com.bootcamp.onlineschool.model;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class StudentGpaNameComparatorTest {
    @Test
    @DisplayName("Should compare students by GPA and name")
    public void testStudentGpaNameComparator() {
        Student student1 = new Student("id1", "Alice", "alice.smith@example.com", 38);
        Student student2 = new Student("id2", "Bob", "bob.johnson@example.com", 83);
        Student student3 = new Student("id3", "alice", "alice.smith@example.com", 83);
        Student student4 = new Student("id4", "Charlie", "charlie.davis@company.com", 55);
        student1.setGpa(3.5);
        student2.setGpa(2.0);
        student3.setGpa(3.5);
        student4.setGpa(3.0);
        Comparator<Student> comparator = new StudentGpaNameComparator();
        assertTrue(comparator.compare(student1, student2) < 0, "Expected Alice to come before Bob");
        assertTrue(comparator.compare(student2, student1) > 0, "Expected Bob to come after Alice");
        assertTrue(comparator.compare(student1, student3) == 0, "Expected Alice to be equal to alice");
        assertTrue(comparator.compare(student4, student2) < 0, "Expected Charlie to come before Bob");
        assertTrue(comparator.compare(null, student1) > 0, "Expected null to come after Alice");
        assertTrue(comparator.compare(student1, null) < 0, "Expected Alice to come before null");
        assertTrue(comparator.compare(null, null) == 0, "Expected null to be equal to null");
    }
}

package com.bootcamp.onlineschool.model;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


public class StudentEmailDomainComparatorTest {
    @Test
    @DisplayName("Should compare students by email domain")
    public void testStudentEmailDomainComparator() {
        Student student1 = new Student("id1", "Alice", "alice.smith@example.com", 38);
        Student student2 = new Student("id2", "Bob", "bob.johnson@company.com", 83);
        Student student3 = new Student("id3", "Alice", "alice.brown@example.com", 45);
        Student student4 = new Student("id4", "Charlie", "charlie.davis@company.com", 55);
        Comparator<Student> comparator = new StudentEmailDomainComparator();
        assertTrue(comparator.compare(student1, student2) > 0, "Expected Alice to come after Bob because company.com sorts before example.com");
        assertTrue(comparator.compare(student2, student1) < 0, "Expected Bob to come before Alice because company.com sorts before example.com");
        assertTrue(comparator.compare(student1, student3) == 0, "Expected Alice to be equal to Alice");
        assertTrue(comparator.compare(student2, student4) < 0, "Expected Bob to come before Charlie");
        assertTrue(comparator.compare(null, student1) > 0, "Expected null to come after Alice");
        assertTrue(comparator.compare(student1, null) < 0, "Expected Alice to come before null");
        assertTrue(comparator.compare(null, null) == 0, "Expected null to be equal to null");

    }
}

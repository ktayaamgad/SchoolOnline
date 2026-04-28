package com.bootcamp.onlineschool;

import com.bootcamp.onlineschool.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for StudentRegistry class
 * Demonstrates:
 * - Testing collections
 * - Testing search and filter operations
 * - Testing sorting
 * - Testing edge cases
 */
@DisplayName("StudentRegistry Class Tests")
public class StudentRegistryTest {
    
    private StudentRegistry registry;
    private Student student1;
    private Student student2;
    private Student student3;
    
    @BeforeEach
    public void setUp() {
        registry = new StudentRegistry();
        student1 = new Student("STU001", "Alice Johnson", "alice@school.edu", 3.8, 33);
        student2 = new Student("STU002", "Bob Smith", "bob@school.edu", 3.5, 34);
        student3 = new Student("STU003", "Charlie Brown", "charlie@school.edu", 3.9, 90);
    }
    
    @Test
    @DisplayName("Should add students to registry")
    public void testAddStudent() {
        registry.addStudent(student1);
        assertEquals(1, registry.getStudentCount());
        
        registry.addStudent(student2);
        assertEquals(2, registry.getStudentCount());
    }
    
    @Test
    @DisplayName("Should throw exception when adding null student")
    public void testAddNullStudent() {
        assertThrows(IllegalArgumentException.class, () -> registry.addStudent(null));
    }
    
    @Test
    @DisplayName("Should throw exception when adding student with invalid email")
    public void testAddStudentWithInvalidEmail() {
        Student invalidStudent = new Student("STU004", "Invalid", "invalid-email", 3.0, 88);
        assertThrows(IllegalArgumentException.class, () -> registry.addStudent(invalidStudent));
    }
    
    @Test
    @DisplayName("Should find student by ID")
    public void testFindStudentById() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        
        Student found = registry.findStudentById("STU001");
        assertNotNull(found);
        assertEquals("Alice Johnson", found.getName());
        
        Student notFound = registry.findStudentById("STU999");
        assertNull(notFound);
    }
    
    @Test
    @DisplayName("Should find students by name")
    public void testFindStudentsByName() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        registry.addStudent(student3);
        
        List<Student> results = registry.findStudentsByName("Charlie");
        assertEquals(1, results.size());
        assertEquals("Charlie Brown", results.get(0).getName());
        
        List<Student> allResults = registry.findStudentsByName("a");
        assertEquals(2, allResults.size()); // Alice and Charlie
    }

    @Test
    @DisplayName("Should find student by email")
    public void testFindStudentByEmail() {

        registry.addStudent(student1);
        registry.addStudent(student2);

        Student foundExact = registry.findStudentByEmail("alice@school.edu");
        assertNotNull(foundExact);
        assertEquals("Alice Johnson", foundExact.getName());

        Student foundCase = registry.findStudentByEmail("BOB@SCHOOL.EDU");
        assertNotNull(foundCase);
        assertEquals("Bob Smith", foundCase.getName());

        Student notFound = registry.findStudentByEmail("nobody@school.edu");
        assertNull(notFound);

        Student nullEmail = registry.findStudentByEmail(null);
        assertNull(nullEmail);

        Student emptyEmail = registry.findStudentByEmail("");
        assertNull(emptyEmail);
    }
    
    @Test
    @DisplayName("Should remove student from registry")
    public void testRemoveStudent() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        assertEquals(2, registry.getStudentCount());
        
        boolean removed = registry.removeStudent("STU001");
        assertTrue(removed);
        assertEquals(1, registry.getStudentCount());
        
        boolean notRemoved = registry.removeStudent("STU999");
        assertFalse(notRemoved);
    }
    
    @Test
    @DisplayName("Should sort students by name")
    public void testSortByName() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        registry.addStudent(student3);
        
        List<Student> sorted = registry.getAllStudentsSortedByName();
        assertEquals(3, sorted.size());
        assertEquals("Alice Johnson", sorted.get(0).getName());
        assertEquals("Bob Smith", sorted.get(1).getName());
        assertEquals("Charlie Brown", sorted.get(2).getName());
    }

    @Test
    @DisplayName("Should sort students with a provided comparator")
    public void testSortByComparator() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        registry.addStudent(student3);

        List<Student> sorted = registry.getAllStudentsSorted(Comparator.comparingDouble(Student::getGpa));
        assertEquals(3, sorted.size());
        assertEquals("Bob Smith", sorted.get(0).getName());
        assertEquals("Alice Johnson", sorted.get(1).getName());
        assertEquals("Charlie Brown", sorted.get(2).getName());
    }

    @Test
    @DisplayName("Should sort students by name case-insensitively")
    public void testSortByNameCaseInsensitive() {
        Student lCStudent = new Student("STU004", "alice johnson", "alice2@school.edu", 3.4, 24);
        Student uCStudent = new Student("STU005", "Bob Smith", "bob2@school.edu", 3.2, 28);
        Student mixCStudent = new Student("STU006", "charlie Brown", "charlie2@school.edu", 3.6, 26);

        registry.addStudent(lCStudent);
        registry.addStudent(uCStudent);
        registry.addStudent(mixCStudent);

        List<Student> sorted = registry.getAllStudentsSortedByName();
        assertEquals(3, sorted.size());
        assertEquals("alice johnson", sorted.get(0).getName());
        assertEquals("Bob Smith", sorted.get(1).getName());
        assertEquals("charlie Brown", sorted.get(2).getName());
    }
    
    @Test
    @DisplayName("Should sort students by GPA descending")
    public void testSortByGpa() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        registry.addStudent(student3);
        
        List<Student> sorted = registry.getAllStudentsSortedByGpa();
        assertEquals(3, sorted.size());
        assertEquals(3.9, sorted.get(0).getGpa());
        assertEquals(3.8, sorted.get(1).getGpa());
        assertEquals(3.5, sorted.get(2).getGpa());
    }
  
    @Test
    @DisplayName("Should calculate average GPA")
    public void testGetAverageGpa() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        registry.addStudent(student3);
        
        double average = registry.getAverageGpa();
        assertEquals((3.8 + 3.5 + 3.9) / 3, average, 0.01);
    }
    
    @Test
    @DisplayName("Should return 0 average GPA for empty registry")
    public void testGetAverageGpaEmpty() {
        assertEquals(0.0, registry.getAverageGpa());
    }
    
    @Test
    @DisplayName("Should clear all students")
    public void testClear() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        assertEquals(2, registry.getStudentCount());
        
        registry.clear();
        assertEquals(0, registry.getStudentCount());
    }

    @Test
    @DisplayName("Should not allow duplicate student IDs")
    public void testDuplicateStudentIds() {
        registry.addStudent(student1);
        Student duplicateIdStudent = new Student("STU001", "Duplicate", "duplicate@ school.edu", 3.0, 88);
        assertThrows(IllegalArgumentException.class, () -> registry.addStudent(duplicateIdStudent));
    }

    @Test
    @DisplayName("Should not allow duplicate student emails")
    public void testDuplicateStudentEmails() {
        registry.addStudent(student1);
        Student duplicateEmailStudent = new Student("STU004", "Duplicate Email", "alice@school.edu", 3.0, 88);
        assertThrows(IllegalArgumentException.class, () -> registry.addStudent(duplicateEmailStudent));
    }

    @Test
    @DisplayName("Should find students by GPA range")
    public void testFindStudentsByGpaRange() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        registry.addStudent(student3);
        List<Student> gpaRange = registry.findStudentsByGpaRange(3.6, 3.9);
        assertEquals(2, gpaRange.size());
        assertThrows(IllegalArgumentException.class,()->registry.findStudentsByGpaRange(4,3));
    }

    @Test
    @DisplayName("Should find students by email domain")
    public void testFindStudentsByEmailDomain() {
        registry.addStudent(student1);
        registry.addStudent(student2);
        registry.addStudent(student3);
        List<Student> domainStudents = registry.findStudentsByEmailDomain("school.edu");
        assertEquals(3, domainStudents.size());
        assertThrows(IllegalArgumentException.class, () -> registry.findStudentsByEmailDomain(null));
        assertThrows(IllegalArgumentException.class, () -> registry.findStudentsByEmailDomain(""));
    }

    @Test
    @DisplayName("Should return GPA distribution")
    public void testGetGpaDistribution() {
        Student s1 = new Student("STU004", "Dave", "dave@school.edu", 3.8, 20); // A
        Student s2 = new Student("STU005", "Eve", "eve@school.edu", 3.2, 21); // B
        Student s3 = new Student("STU006", "Frank", "frank@school.edu", 2.5, 22); // C
        Student s4 = new Student("STU007", "Grace", "grace@school.edu", 1.5, 23); // D
        Student s5 = new Student("STU008", "Heidi", "heidi@school.edu", 0.5, 24); // F
        Student s6 = new Student("STU009", "Ivan", "ivan@school.edu", 3.9, 25); // A

        registry.addStudent(s1);
        registry.addStudent(s2);
        registry.addStudent(s3);
        registry.addStudent(s4);
        registry.addStudent(s5);
        registry.addStudent(s6);

        Map<String, Integer> distribution = registry.getGpaDistribution();
        assertEquals(2, distribution.get("A").intValue());
        assertEquals(1, distribution.get("B").intValue());
        assertEquals(1, distribution.get("C").intValue());
        assertEquals(1, distribution.get("D").intValue());
        assertEquals(1, distribution.get("F").intValue());
    }

    @Test
    @DisplayName("Should return empty distribution for empty registry")
    public void testGetGpaDistributionEmpty() {
        Map<String, Integer> distribution = registry.getGpaDistribution();
        assertTrue(distribution.isEmpty());
    }

    @Test
    @DisplayName("Should return top N students by GPA")
    public void testGetTopStudents() {
        Student s1 = new Student("STU004", "Dave", "dave@school.edu", 3.8, 20);
        Student s2 = new Student("STU005", "Eve", "eve@school.edu", 3.2, 21);
        Student s3 = new Student("STU006", "Frank", "frank@school.edu", 2.5, 22);

        registry.addStudent(student1); // 3.8
        registry.addStudent(student2); // 3.5
        registry.addStudent(student3); // 3.9
        registry.addStudent(s1); // 3.8
        registry.addStudent(s2); // 3.2
        registry.addStudent(s3); // 2.5

        List<Student> top3 = registry.getTopStudents(3);
        assertEquals(3, top3.size());
        assertEquals("Charlie Brown", top3.get(0).getName()); // 3.9
        assertEquals("Alice Johnson", top3.get(1).getName()); // 3.8
        assertEquals("Dave", top3.get(2).getName()); // 3.8
    }

    @Test
    @DisplayName("Should return all students when N > total students")
    public void testGetTopStudentsMoreThanTotal() {
        registry.addStudent(student1);
        registry.addStudent(student2);

        List<Student> top10 = registry.getTopStudents(10);
        assertEquals(2, top10.size());
    }

    @Test
    @DisplayName("Should return empty list for N = 0")
    public void testGetTopStudentsZero() {
        registry.addStudent(student1);
        registry.addStudent(student2);

        List<Student> top0 = registry.getTopStudents(0);
        assertTrue(top0.isEmpty());
    }

    @Test
    @DisplayName("Should throw exception for negative N")
    public void testGetTopStudentsNegative() {
        assertThrows(IllegalArgumentException.class, () -> registry.getTopStudents(-1));
    }

    @Test
    @DisplayName("Should return students above GPA percentile")
    public void testGetStudentsByGpaPercentile() {
        Student s1 = new Student("STU004", "Dave", "dave@school.edu", 1.0, 20);
        Student s2 = new Student("STU005", "Eve", "eve@school.edu", 2.0, 21);
        Student s3 = new Student("STU006", "Frank", "frank@school.edu", 3.0, 22);
        Student s4 = new Student("STU007", "Grace", "grace@school.edu", 4.0, 23);

        registry.addStudent(s1); // 1.0
        registry.addStudent(s2); // 2.0
        registry.addStudent(s3); // 3.0
        registry.addStudent(s4); // 4.0

        // 50th percentile: sorted [1.0, 2.0, 3.0, 4.0], index = ceil(0.5 * 3) = 2, threshold = 3.0
        List<Student> above50th = registry.getStudentsByGpaPercentile(50.0);
        assertEquals(1, above50th.size());
        assertEquals("Grace", above50th.get(0).getName()); // 4.0 > 3.0

        // 0th percentile: threshold = 1.0, all > 1.0
        List<Student> above0th = registry.getStudentsByGpaPercentile(0.0);
        assertEquals(3, above0th.size());

        // 100th percentile: threshold = 4.0, none > 4.0
        List<Student> above100th = registry.getStudentsByGpaPercentile(100.0);
        assertTrue(above100th.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for percentile on empty registry")
    public void testGetStudentsByGpaPercentileEmpty() {
        List<Student> result = registry.getStudentsByGpaPercentile(50.0);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for single student at 100th percentile")
    public void testGetStudentsByGpaPercentileSingleStudent() {
        registry.addStudent(student1);
        List<Student> result = registry.getStudentsByGpaPercentile(100.0);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should throw exception for invalid percentile")
    public void testGetStudentsByGpaPercentileInvalid() {
        assertThrows(IllegalArgumentException.class, () -> registry.getStudentsByGpaPercentile(-1.0));
        assertThrows(IllegalArgumentException.class, () -> registry.getStudentsByGpaPercentile(101.0));
    }
}


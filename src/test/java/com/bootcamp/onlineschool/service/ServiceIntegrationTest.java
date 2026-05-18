package com.bootcamp.onlineschool.service;

import com.bootcamp.onlineschool.model.Course;
import com.bootcamp.onlineschool.model.Student;
import com.bootcamp.onlineschool.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Service Integration Test")
public class ServiceIntegrationTest {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @BeforeEach
    public void setUp() {
        // Clear any previous test state before creating fresh data
        studentService.clear();
        courseService.clear();
        teacherService.clear();
        departmentService.clear();

        // Setup Students
        Student student1 = new Student("STU001", "John Doe", "john.doe@school.com", 3.8);
        Student student2 = new Student("STU002", "Jane Smith", "jane.smith@school.com", 3.9);
        Student student3 = new Student("STU003", "Bob Johnson", "bob.johnson@school.com", 3.5);
        
        studentService.addStudent(student1);
        studentService.addStudent(student2);
        studentService.addStudent(student3);
        
        courseService.createCourse("CS101", "Introduction to Computer Science", 3, "Dr. Anderson", 30);
        courseService.createCourse("MATH201", "Calculus II", 4, "Prof. Brown", 25);
        courseService.createCourse("ENG102", "English Literature", 3, "Ms. Davis", 28);
        
        Teacher teacher1 = new Teacher(1, "Dr. Anderson", "anderson@school.com", "Computer Science", 10);
        Teacher teacher2 = new Teacher(2, "Prof. Brown", "brown@school.com", "Mathematics", 15);
        Teacher teacher3 = new Teacher(3, "Ms. Davis", "davis@school.com", "English", 8);
        
        teacherService.addTeacher(teacher1);
        teacherService.addTeacher(teacher2);
        teacherService.addTeacher(teacher3);
        
        List<Integer> csTeachers = new ArrayList<>();
        csTeachers.add(1);
        departmentService.createDepartment(1, "Computer Science", "Dr. Anderson", 500000, csTeachers);
        
        List<Integer> mathTeachers = new ArrayList<>();
        mathTeachers.add(2);
        departmentService.createDepartment(2, "Mathematics", "Prof. Brown", 400000, mathTeachers);
        
        List<Integer> englishTeachers = new ArrayList<>();
        englishTeachers.add(3);
        departmentService.createDepartment(3, "English", "Ms. Davis", 350000, englishTeachers);
    }
    
    @Test
    @DisplayName("Test Student-Course-Teacher-Department Integration")
    public void testStudentCourseTeacherDepartmentIntegration() {
        // Test student retrieval
        Student student = studentService.findStudentById("STU001");
        assertNotNull(student);
        assertEquals("John Doe", student.getName());
        // Test course retrieval
        var course = courseService.getCourseById("CS101");
        assertNotNull(course);
        assertEquals("Introduction to Computer Science", course.getCourseName());
        // Test teacher retrieval
        var teacher = teacherService.findTeacherById(1);
        assertNotNull(teacher);
        assertEquals("Dr. Anderson", teacher.getName());
        // Test department retrieval
        var department = departmentService.getDepartmentById(1);
        assertNotNull(department);
        assertEquals("Computer Science", department.getName());
    }

    @Test
    @DisplayName("Enroll students in courses")
    public void testCourseEnrollmentAndDepartmentBudget() {
        boolean enrolled = courseService.enrollStudent("CS101");
        assertTrue(enrolled);
        var course = courseService.getCourseById("CS101");
        assertEquals(1, course.getEnrolledStudents());
    }

    @Test
    @DisplayName("Find all teachers in a department")
    public void testFindAllTeachersInDepartment() {
        List<Teacher> csTeachers = teacherService.findTeachersByDepartment("Computer Science");

        assertNotNull(csTeachers);
        assertEquals(1, csTeachers.size());
        assertEquals("Dr. Anderson", csTeachers.get(0).getName());
        assertEquals("Computer Science", csTeachers.get(0).getDepartment());
    }

    @Test
    @DisplayName("Find all courses taught by a specific teacher")
    public void testFindAllCoursesTaughtBySpecificTeacher() {
        Teacher teacher = teacherService.findTeacherById(1);

        List<Course> coursesByTeacher = courseService.getAllCourses().stream()
                .filter(course -> course.getInstructor().equals(teacher.getName()))
                .toList();

        assertEquals(1, coursesByTeacher.size());
        assertEquals("CS101", coursesByTeacher.get(0).getCourseId());
        assertEquals("Introduction to Computer Science", coursesByTeacher.get(0).getCourseName());
    }

    @Test
    @DisplayName("Verify service counts and relationships are consistent")
    public void testServiceCountsAndRelationshipsAreConsistent() {
        assertEquals(3, studentService.getTotalStudents());
        assertEquals(3, courseService.getTotalCourses());
        assertEquals(3, teacherService.getAllTeachers().size());
        assertEquals(3, departmentService.getAllDepartments().size());

        int totalEnrolled = courseService.getAllCourses()
                .stream()
                .mapToInt(Course::getEnrolledStudents)
                .sum();
        assertEquals(0, totalEnrolled);

        List<String> teacherNames = teacherService.getAllTeachers()
                .stream()
                .map(Teacher::getName)
                .toList();

        courseService.getAllCourses().forEach(course ->
                assertTrue(teacherNames.contains(course.getInstructor()),
                        "Course instructor must be a registered teacher"));

        departmentService.getAllDepartments().forEach(department ->
                department.getTeacherId().forEach(teacherId -> {
                    Teacher assignedTeacher = teacherService.findTeacherById(teacherId);
                    assertNotNull(assignedTeacher, "Assigned teacher must exist");
                    assertEquals(department.getName(), assignedTeacher.getDepartment(),
                            "Assigned teacher department must match department name");
                }));
    }

    @Test
    @DisplayName("Complete workflow from department through teachers, courses, and students")
    public void testCompleteWorkflowFromDepartmentToStudents() {
        Teacher newTeacher = new Teacher(4, "Dr. Newton", "newton@school.com", "Physics", 12);
        teacherService.addTeacher(newTeacher);

        departmentService.createDepartment(4, "Physics", "Dr. Newton", 300000, List.of(4));

        courseService.createCourse("PHY301", "Classical Mechanics", 4, "Dr. Newton", 20);

        Student newStudent = new Student("STU004", "Alice Walker", "alice.walker@school.com", 3.7);
        studentService.addStudent(newStudent);

        assertEquals(4, teacherService.getAllTeachers().size());
        assertEquals(4, departmentService.getAllDepartments().size());
        assertEquals(4, studentService.getTotalStudents());
        assertEquals(4, courseService.getTotalCourses());

        List<Teacher> physicsTeachers = teacherService.findTeachersByDepartment("Physics");
        assertEquals(1, physicsTeachers.size());
        assertEquals("Dr. Newton", physicsTeachers.get(0).getName());

        List<Course> newCourses = courseService.getAllCourses().stream()
                .filter(course -> course.getInstructor().equals("Dr. Newton"))
                .toList();
        assertEquals(1, newCourses.size());
        assertEquals("PHY301", newCourses.get(0).getCourseId());

        boolean enrolled = courseService.enrollStudent("PHY301");
        assertTrue(enrolled);
        assertEquals(1, courseService.getCourseById("PHY301").getEnrolledStudents());
    }

    @Test
    @DisplayName("Validate data across services and detect orphaned course instructors")
    public void testValidateDataAcrossServices() {
        courseService.createCourse("HIS101", "World History", 3, "Miss Unknown", 30);

        List<String> registeredTeacherNames = teacherService.getAllTeachers().stream()
                .map(Teacher::getName)
                .toList();

        boolean hasOrphanInstructor = courseService.getAllCourses().stream()
                .anyMatch(course -> !registeredTeacherNames.contains(course.getInstructor()));

        assertTrue(hasOrphanInstructor, "Course with unregistered instructor should be detected");
        assertFalse(registeredTeacherNames.contains("Miss Unknown"));
    }

    @Test
    @DisplayName("Handle multi-service exceptions during cross-service operations")
    public void testExceptionHandlingInMultiServiceOperations() {
        assertThrows(CourseService.CourseAlreadyExistsException.class,
                () -> courseService.createCourse("CS101", "Duplicate Course", 3, "Dr. Anderson", 30));

        assertThrows(DepartmentService.DepartmentAlreadyExistsException.class,
                () -> departmentService.createDepartment(1, "Computer Science", "Dr. Anderson", 500000, List.of(1)));

        assertThrows(DepartmentService.DepartmentNotFoundException.class,
                () -> departmentService.assignTeacherToDepartment(999, 1));
    }

    @Test
    @DisplayName("Maintain state consistency after complex operations")
    public void testStateConsistencyAfterComplexOperations() {
        courseService.enrollStudent("CS101");
        courseService.enrollStudent("CS101");
        departmentService.assignTeacherToDepartment(1, 2);
        departmentService.removeTeacherFromDepartment(1, 1);

        assertEquals(2, courseService.getCourseById("CS101").getEnrolledStudents());
        assertEquals(1, departmentService.getDepartmentById(1).getTeacherId().size());

        Teacher removedTeacher = teacherService.findTeacherById(1);
        assertNotNull(removedTeacher);
        assertEquals("Computer Science", removedTeacher.getDepartment());

        assertTrue(departmentService.getDepartmentById(1).getTeacherId().contains(2));
        assertFalse(departmentService.getDepartmentById(1).getTeacherId().contains(1));
    }

    @Test
    @DisplayName("Find all students in courses of a department")
    public void testFindAllStudentsInCoursesOfDepartment() {
        var department = departmentService.getDepartmentById(1);

        List<String> teacherNames = department.getTeacherId().stream()
                .map(id -> teacherService.findTeacherById(id))
                .map(Teacher::getName)
                .toList();

        List<Course> departmentCourses = courseService.getAllCourses().stream()
                .filter(course -> teacherNames.contains(course.getInstructor()))
                .toList();

        assertFalse(departmentCourses.isEmpty());
        assertEquals(1, departmentCourses.size());
        assertEquals("CS101", departmentCourses.get(0).getCourseId());

        // Enroll students in the department course and verify enrollment as a cross-service query result.
        courseService.enrollStudent("CS101");
        courseService.enrollStudent("CS101");

        int totalEnrolled = departmentCourses.stream()
                .mapToInt(Course::getEnrolledStudents)
                .sum();

        assertEquals(2, totalEnrolled);
    }

    @Test
    @DisplayName("Remove teacher and verify teacher updates")
    public void testRemoveTeacherAndVerifyCourseUpdates() {
        teacherService.removeTeacherById(1);
        var course = courseService.getCourseById("CS101");
        assertNotNull(course);
        assertEquals("Dr. Anderson", course.getInstructor());
        assertNull(teacherService.findTeacherById(1));
    }

    @Test
    @DisplayName("Remove department and verify department deletion")
    public void testRemoveDepartmentAndVerifyTeacherUpdates() {
        departmentService.DeleteDepartment(1);
        assertThrows(DepartmentService.DepartmentNotFoundException.class, () -> departmentService.getDepartmentById(1));
        var teacher = teacherService.findTeacherById(1);
        assertNotNull(teacher);
        assertEquals("Computer Science", teacher.getDepartment());
    }
}


package com.bootcamp.onlineschool.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.bootcamp.onlineschool.model.Department;
import com.bootcamp.onlineschool.service.DepartmentService.DepartmentAlreadyExistsException;
import com.bootcamp.onlineschool.service.DepartmentService.DepartmentNotFoundException;

@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        departmentService.clear();
    }

    @Test
    @DisplayName("Should create department successfully")
    public void testCreateDepartment() {
        Department department = departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        assertNotNull(department);
        assertEquals(1, department.getId());
        assertEquals("Computer Science", department.getName());
        assertEquals("Dr. Smith", department.getHead());
        assertEquals(100000, department.getBudget());
        assertEquals(List.of(1, 2), department.getTeacherId());

    }

    @Test
    @DisplayName("Should throw exception when creating duplicate department")
    public void testCreateDuplicateDepartment() {
        departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        assertThrows(DepartmentAlreadyExistsException.class, () -> departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2)));
    }

    @Test
    @DisplayName("Should get department by ID")
    public void testGetDepartmentById() {
        Department department = departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        Department foundDepartment = departmentService.getDepartmentById(1);
        assertNotNull(foundDepartment);
        assertEquals(department.getId(), foundDepartment.getId());
    }

    @Test
    @DisplayName("Should throw exception when getting non-existent department")
    public void testGetNonExistentDepartment() {
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentById(999));
    }

    @Test
    @DisplayName("should assign teacher to department")
    public void testAssignTeacherToDepartment() {
         departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        departmentService.assignTeacherToDepartment(1, 3);   
        List<Integer> teacherIds = departmentService.getDepartmentById(1).getTeacherId();
        assertTrue(teacherIds.contains(3));
    }

    @Test
    @DisplayName("should remove teacher from department")
    public void testRemoveTeacherFromDepartment() {
        departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        departmentService.removeTeacherFromDepartment(1, 2);   
        List<Integer> teacherIds = departmentService.getDepartmentById(1).getTeacherId();
        assertFalse(teacherIds.contains(2));
    }

    @Test
    @DisplayName("should get departments by budget range") 
    public void testGetDepartmentsByBudgetRange() {
        departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        departmentService.createDepartment(2, "Mathematics", "Dr. Johnson", 50000, List.of(3));
        List<Department> departments = departmentService.getDepartmentsByBudgetRange(60000, 150000);
        assertEquals(1, departments.size());
        assertEquals("Computer Science", departments.get(0).getName());
    }

    @Test
    @DisplayName("should get total budget")
    public void testGetTotalBudget() {
        departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        departmentService.createDepartment(2, "Mathematics", "Dr. Johnson", 50000, List.of(3));
        int totalBudget = departmentService.getTotalBudget();
        assertEquals(150000, totalBudget);
    }
    
    
    @Test
    @DisplayName("should throw exception when deleting non-existent department")
    public void testDeleteNonExistentDepartment() {
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.DeleteDepartment(999));
    }

    @Test
    @DisplayName("should throw exception when already existing department is deleted")
    public void testDeleteExistingDepartment() {
        departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2));
        assertThrows(DepartmentAlreadyExistsException.class, () -> departmentService.createDepartment(1, "Computer Science", "Dr. Smith", 100000, List.of(1, 2)));
    }

}



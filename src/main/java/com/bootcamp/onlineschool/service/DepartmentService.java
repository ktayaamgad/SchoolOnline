package com.bootcamp.onlineschool.service;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.bootcamp.onlineschool.model.Department;

@Service
public class DepartmentService {

    private final HashMap<Integer, Department> departmentMap = new HashMap<>();

    public Department createDepartment(int id, String name, String head, int budget,
         List<Integer> teacherId) {
        if (departmentMap.containsKey(id)) {
            throw new DepartmentAlreadyExistsException("Department already exists with id: "
             + id);
        }
        Department department = new Department(id, name, head, budget, new ArrayList<>(teacherId));
        departmentMap.put(id, department);
        return department;
    }

    public Department getDepartmentById(int id) {
        Department department = departmentMap.get(id);
        if (department == null) {
            throw new DepartmentNotFoundException("Department not found with id: " + id);
        }
        return department;
    }

    public List<Department> getAllDepartments() {
        return List.copyOf(departmentMap.values());
    }

    public void assignTeacherToDepartment(int departmentId, int teacherId) {
        Department department = getDepartmentById(departmentId);
        List<Integer> teacherIds = department.getTeacherId();
        if (!teacherIds.contains(teacherId)) {
            teacherIds.add(teacherId);
        }
    }

    public void removeTeacherFromDepartment(int departmentId, int teacherId) {
        Department department = getDepartmentById(departmentId);
        List<Integer> teacherIds = department.getTeacherId();
        teacherIds.remove(Integer.valueOf(teacherId));
    }

    public List<Department> getDepartmentsByBudgetRange(int minBudget, int maxBudget) {
        return departmentMap
                .values()
                .stream()
                .filter(department -> department.getBudget() >= minBudget && department.getBudget() <= maxBudget)
                .toList();
    }

    public int getTotalBudget() {
        return departmentMap
                .values()
                .stream()
                .mapToInt(Department::getBudget)
                .sum();
    }
    
    public void DeleteDepartment(int id) {
        if (!departmentMap.containsKey(id)) {
            throw new DepartmentNotFoundException("Department not found with id: " + id);
        }
        departmentMap.remove(id);
    }

    public void clear() {
        departmentMap.clear();
    }

    public static class DepartmentNotFoundException extends RuntimeException {
        public DepartmentNotFoundException(String message) {
            super(message);
        }
    }

    public static class DepartmentAlreadyExistsException extends RuntimeException {
        public DepartmentAlreadyExistsException(String message) {
            super(message);
        }
    }
}

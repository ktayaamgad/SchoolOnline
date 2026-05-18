package com.bootcamp.onlineschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bootcamp.onlineschool.TeacherRegistry;
import com.bootcamp.onlineschool.model.Teacher;

@Service
public class TeacherService {
    
    private final TeacherRegistry teacherRegistry;

    public TeacherService(TeacherRegistry teacherRegistry) {
        this.teacherRegistry = teacherRegistry;
    }

    public void addTeacher(Teacher teacher){
        if(teacher == null){
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        teacherRegistry.addTeacher(teacher);
    }

    public Teacher findTeacherById(int id){
        return teacherRegistry.findTeacherById(id);
    }
    
    public void removeTeacherById(int id){
        teacherRegistry.removeTeacherById(id);
    }

    public List<Teacher> findTeachersByDepartment(String department){
        return teacherRegistry.findTeacherByDepartment(department);
    }

    public List<Teacher> findTeachersByExperience(int yearsOfExperience){
        return teacherRegistry.findTeacherByExperience(yearsOfExperience);
    }

    public List<Teacher> getAllTeachers(){
        return teacherRegistry.getAllTeachers();
    }

    public boolean isEmpty() {
        return teacherRegistry.isEmpty();
    }

    public void clear() {
        teacherRegistry.clear();
    }

    public static class TeacherNotFoundException extends RuntimeException {
        public TeacherNotFoundException(String message) {
            super(message);
        }
    }

    public Object findTeacherByName(String instructor) {
        for (Teacher teacher : teacherRegistry.getAllTeachers()) {
            if (teacher.getName().equals(instructor)) {
                return teacher;
            }
        }
        return null;
    }
}

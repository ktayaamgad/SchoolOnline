package com.bootcamp.onlineschool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bootcamp.onlineschool.model.Teacher;

public class TeacherRegistry {

    HashMap<Integer, Teacher> teacherMap;

    public TeacherRegistry() {
        this.teacherMap = new HashMap<>();
    }

    public List<Teacher> getAllTeachers(){
        return new ArrayList<>(teacherMap.values());
    }

    public void addTeacher(Teacher teacher){
        teacherMap.put(teacher.getId(), teacher);
    }

    public Teacher findTeacherById(int id){
        return teacherMap.get(id);
    }

    public boolean isEmpty() {
        return teacherMap.isEmpty();
    }

    public void removeTeacherById(int id){
        Teacher teacherRemoved = teacherMap.remove(id) ;
        if((teacherRemoved != null)){
            System.out.println("Teacher with ID " + id + " removed.");
        } else {
            System.out.println("No teacher found with ID: " + id);
        }
    }

    public List<Teacher> findTeacherByDepartment(String department){
        System.out.println("Teachers in Department: " + department);
        List<Teacher> teachers = new ArrayList<>();
        for(Teacher teacher : teacherMap.values()){
            if(teacher.getDepartment().equals(department)){
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    public List<Teacher> findTeacherByExperience(int yearsOfExperience){
        System.out.println("Teachers with " + yearsOfExperience + " years of experience:");
        List<Teacher> teachers = new ArrayList<>();
        for(Teacher teacher : teacherMap.values()){
            if(teacher.getYearsOfExperience() == yearsOfExperience){
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    public int getTeacherCount(){
        return teacherMap.size();
    }

    public void clear(){
        teacherMap.clear();
    }
}

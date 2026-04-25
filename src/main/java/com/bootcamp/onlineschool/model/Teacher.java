package com.bootcamp.onlineschool.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {
    private String department;
    private List<Course> coursesTaught;
    public Teacher(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
        coursesTaught = new ArrayList<>();
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }
    public void addCourse(Course course) {
        coursesTaught.add(course);
    }
    public void removeCourse(Course course) {
        coursesTaught.remove(course);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                ", coursesTaught=" + coursesTaught +
                '}';
    }

    public String getRole() {
        return "Teacher";
    }
}

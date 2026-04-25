package com.bootcamp.onlineschool.model;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Map;

/**
 * Student class demonstrating:
 * - Encapsulation (private fields, public getters/setters)
 * - Constructor overloading
 * - toString() method
 * - equals() and hashCode() methods
 */
public class Student extends User {
    private double gpa;
    private int age;
    private List<Course> enrolledCourses;

    
    // Constructor with required fields
    public Student(String studentId, String name, String email,int age) {
        super(studentId, name, email);
        this.gpa = 0.0;
        this.age=age;
        this.enrolledCourses = new ArrayList<>();
    }
    
    // Constructor with all fields
    public Student(String studentId, String name, String email, double gpa,int age) {
        super(studentId, name, email);
        this.gpa = gpa;
        this.age=age;
        this.enrolledCourses = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getStudentId() {
        return getId();
    }
    
    public void setStudentId(String studentId) {
        setId(studentId);
    }

    public int getAge(){
        return age;
    }
    
    public void setAge(int age){
        if (age >= 16 && age <= 100) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("age must be between 16 and 100");
        }
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        } else {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
    }
    
    // Validate email format
    public boolean isValidEmail() {
        return getEmail() != null && getEmail().contains("@") && getEmail().contains(".") 
        && getEmail().endsWith("@school.edu");
    }

    public boolean isValidAge(){
        return age>=16 && age<=100;
    }
    
    @Override
    public String toString() {
        return String.format("Student{id='%s', name='%s', email='%s', gpa=%.2f, age='%d'}", 
                getId(), getName(), getEmail(), gpa, age);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    
    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            this.enrolledCourses.add(course);
        }
        else {
            throw new IllegalArgumentException("Student is already enrolled in this course.");
        }
    }

    public void dropCourse(String courseId) {
        for (int i = 0; i < enrolledCourses.size(); i++) {
            if (this.enrolledCourses.get(i).getCourseId().equals(courseId)) {
                this.enrolledCourses.remove(i);
                return; 
            }
        }
        throw new IllegalArgumentException("Student is not enrolled in this course.");
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public int getTotalCredits() {
        int total = 0;
        for (Course course : enrolledCourses) {
            total += course.getCredits();
        }
        return total;
    }

    public String getRole() {
        return "Student";
    }
    
    public void calculateGpa(Map<Course, String> grades) {
        if (grades == null || grades.isEmpty()) {
            throw new IllegalArgumentException("Grades map cannot be null or empty");
        }

        double totalGradePoints = 0.0;
        int totalCredits = 0;

        for (Map.Entry<Course, String> entry : grades.entrySet()) {
            Course course = entry.getKey();
            String letterGrade = entry.getValue().toUpperCase(); 
            double Value = 0.0;
            
            switch (letterGrade) {
                case "A": Value = 4.0; break;
                case "B": Value = 3.0; break;
                case "C": Value = 2.0; break;
                case "D": Value = 1.0; break;
                case "F": Value = 0.0; break;
                default: 
                    throw new IllegalArgumentException("Invalid grade provided: " + letterGrade);
            }
            
            totalGradePoints += (Value * course.getCredits());
            totalCredits += course.getCredits();
        }

        if (totalCredits > 0) {
            this.gpa = totalGradePoints / totalCredits;
        } else {
            this.gpa = 0.0;
        }
    }
}

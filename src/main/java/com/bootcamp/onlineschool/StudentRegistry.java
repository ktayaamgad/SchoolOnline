package com.bootcamp.onlineschool;

import com.bootcamp.onlineschool.model.Student;
import com.bootcamp.onlineschool.model.StudentNameComparator;
import java.util.*;
import java.util.stream.Collectors;

public class StudentRegistry {
    private List<Student> students;
    private Map<String, Student> studentMap,studentMap1;
    
    public StudentRegistry() {
        this.students = new ArrayList<>();
        this.studentMap = new HashMap<>();
        this.studentMap1 = new HashMap<>();
    }
    
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (!student.isValidEmail()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (studentMap.containsKey(student.getStudentId())) {
            throw new IllegalArgumentException("Student with this ID already exists");
        }
        if (studentMap1.containsKey(student.getEmail().toLowerCase())) {
            throw new IllegalArgumentException("Student with this email already exists");
        }
        students.add(student);
        studentMap.put(student.getStudentId(), student);
        studentMap1.put(student.getEmail().toLowerCase(), student);
    }
    
    public boolean removeStudent(String studentId) {
        Student student = studentMap.remove(studentId);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
    }
    
    public Student findStudentById(String studentId) {
        return studentMap.get(studentId);
    }

    public Student findStudentByEmail(String studentEmail){
        if (studentEmail == null) {
            return null;
        }
        return studentMap1.get(studentEmail.toLowerCase());
    }
    
    public List<Student> findStudentsByName(String name) {
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    
    public List<Student> getAllStudentsSortedByName() {
        return students.stream()
                .sorted(new StudentNameComparator())
                .collect(Collectors.toList());
    }

    
    public List<Student> getAllStudentsSorted(Comparator<Student> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
    
    
    public List<Student> getAllStudentsSortedByGpa() {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .collect(Collectors.toList());
    }


    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in registry");
            return;
        }
        for(Student student : students) {
            System.out.println(student);
        }
    }
    
    public int getStudentCount() {
        return students.size();
    }
    
    public double getAverageGpa() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Student s : students) {
            if (s.getGpa() < 0.0 || s.getGpa() > 4.0) {
                throw new IllegalStateException("Invalid GPA found: " + s.getGpa());
            }
            sum += s.getGpa();
        }
        return sum / students.size();
    }


    public List<Student> findStudentsByGpaRange(double min, double max)
    {
        if(min>max)
            throw new IllegalArgumentException("Min GPA cannot be greater than Max GPA");
        List<Student> result = new ArrayList<>();
        for(Student s:students)        {
            if(s.getGpa()>=min && s.getGpa()<=max)
                result.add(s);
        }
        return result;
    }

    public List<Student> findStudentsByEmailDomain(String domain)
    {
        if(domain == null || domain.trim().isEmpty())
            throw new IllegalArgumentException("Domain cannot be null or empty");
        List<Student> result = new ArrayList<>();
        for(Student s:students)  {
            if(s.getEmail().toLowerCase().endsWith("@" + domain.toLowerCase()))
                result.add(s);
        }
        return result;
    }
    
    public void clear() {
        students.clear();
        studentMap.clear();
        studentMap1.clear();
    }

    public Map<String, Integer> getGpaDistribution() {
        return students.stream()
                .collect(Collectors.groupingBy(
                        this::getGpaGrade,
                        Collectors.summingInt(s -> 1)
                ));
    }

    private String getGpaGrade(Student student) {
        double gpa = student.getGpa();
        if (gpa >= 3.7 && gpa <= 4.0) return "A";
        if (gpa >= 2.7 && gpa < 3.7) return "B";
        if (gpa >= 1.7 && gpa < 2.7) return "C";
        if (gpa >= 1.0 && gpa < 1.7) return "D";
        if (gpa >= 0.0 && gpa < 1.0) return "F";
        throw new IllegalStateException("Invalid GPA: " + gpa);
    }

    public List<Student> getTopStudents(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n cannot be negative");
        }
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsByGpaPercentile(double percentile) {
        if (percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("Percentile must be between 0 and 100");
        }
        if (students.isEmpty()) {
            return new ArrayList<>();
        }
        List<Double> gpas = students.stream()
                .map(Student::getGpa)
                .sorted()
                .collect(Collectors.toList());
        int size = gpas.size();
        int index = (int) Math.ceil((percentile / 100.0) * (size - 1));
        double threshold = gpas.get(index);
        return students.stream()
                .filter(s -> s.getGpa() > threshold)
                .collect(Collectors.toList());
    }
}

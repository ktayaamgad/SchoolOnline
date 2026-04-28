package com.bootcamp.onlineschool.model;
import java.util.Comparator;

public class StudentGpaNameComparator implements Comparator<Student> {

    @Override
    public int compare(Student first, Student second) {
        if (first == second) {
            return 0;
        }
        if (first == null) {
            return 1;
        }
        if (second == null) {
            return -1;
        }

        int gpaComparison = Double.compare(second.getGpa(), first.getGpa());
        if (gpaComparison != 0) {
            return gpaComparison;
        }
        return new StudentNameComparator().compare(first, second);
    }
    
}

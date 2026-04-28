package com.bootcamp.onlineschool.model;

import java.util.Comparator;

public class StudentNameComparator implements Comparator<Student> {

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

        String firstName = first.getName();
        String secondName = second.getName();
        if (firstName == secondName) {
            return 0;
        }
        if (firstName == null) {
            return 1;
        }
        if (secondName == null) {
            return -1;
        }
        return firstName.compareToIgnoreCase(secondName);
    }
}

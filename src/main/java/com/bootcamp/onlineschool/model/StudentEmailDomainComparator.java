package com.bootcamp.onlineschool.model;
import java.util.Comparator;


public class StudentEmailDomainComparator implements Comparator<Student> {
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

        String firstEmail = first.getEmail();
        String secondEmail = second.getEmail();
        if (firstEmail == secondEmail) {
            return 0;
        }
        if (firstEmail == null) {
            return 1;   
        }
        if (secondEmail == null) {
            return -1;
        }

        String firstDomain = getDomain(firstEmail);
        String secondDomain = getDomain(secondEmail);
        if (firstDomain == secondDomain) {
            return 0;
        }
        if (firstDomain == null) {
            return 1;   
        }
        if (secondDomain == null) {
            return -1;
        }
        int domainComparison = firstDomain.compareToIgnoreCase(secondDomain);
        if (domainComparison != 0) {
            return domainComparison;
        }
        return new StudentNameComparator().compare(first, second);
    }

    private String getDomain(String email) {
        int atIndex = email.lastIndexOf('@');
        if (atIndex != -1 && atIndex < email.length() - 1) {
            return email.substring(atIndex + 1).toLowerCase();
        }
        return null;
    }
}

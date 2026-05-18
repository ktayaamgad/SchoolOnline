package com.bootcamp.onlineschool.model;

import java.util.List;

public class Department {
    private int id;
    private String name;
    private String head;
    private int budget;
    private List<Integer> teacherId;
    
    public Department(int id, String name, String head, int budget, List<Integer> teacherId) {
        this.id = id;
        this.name = name;
        this.head = head;
        this.budget = budget;
        this.teacherId = teacherId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setTeacherId(List<Integer> teacherId) {
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHead() {
        return head;
    }

    public int getBudget() {
        return budget;
    }

    public List<Integer> getTeacherId() {
        return teacherId;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", head='" + head + '\'' +
                ", budget=" + budget +
                ", teacherId=" + teacherId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != that.id) return false;
        if (budget != that.budget) return false;
        if (!name.equals(that.name)) return false;
        if (!head.equals(that.head)) return false;
        return teacherId.equals(that.teacherId);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + head.hashCode();
        result = 31 * result + budget;
        result = 31 * result + teacherId.hashCode();
        return result;
    }

    public void addTeacher(int teacherId) {
        this.teacherId.add(teacherId);
    }

    public void removeTeacher(int teacherId) {
        this.teacherId.remove(Integer.valueOf(teacherId));
    }

    public int getTeacherCount() {
        return this.teacherId.size();
    }

    public boolean isWithinBudget(int amount) {
        return this.budget >= amount;
    }

    
}

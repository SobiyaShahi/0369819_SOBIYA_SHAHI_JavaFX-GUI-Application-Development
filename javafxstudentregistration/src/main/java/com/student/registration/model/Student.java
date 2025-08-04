package com.student.registration.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    private String name;
    private String email;
    private String program;
    private String semester;
    private List<Course> registeredCourses;
    
    public Student() {
        this.registeredCourses = new ArrayList<>();
    }
    
    public Student(String name, String email, String program, String semester) {
        this.name = name;
        this.email = email;
        this.program = program;
        this.semester = semester;
        this.registeredCourses = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public List<Course> getRegisteredCourses() { return registeredCourses; }
    public void setRegisteredCourses(List<Course> registeredCourses) { 
        this.registeredCourses = registeredCourses; 
    }
    
    public void addCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            registeredCourses.add(course);
        }
    }
    
    public void removeCourse(Course course) {
        registeredCourses.remove(course);
    }
    
    public boolean isRegisteredFor(Course course) {
        return registeredCourses.contains(course);
    }
}

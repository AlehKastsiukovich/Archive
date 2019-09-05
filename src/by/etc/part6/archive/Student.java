package by.etc.part6.archive;


import java.io.Serializable;


public class Student implements Serializable {
    private String firstName;
    private String secondName;
    private String faculty;
    private int course;
    private int age;


    public Student(String firstName, String secondName, String faculty, int course, int age) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.faculty = faculty;
        this.course = course;
        this.age = age;
    }

    public Student() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String toString() {
        return firstName + " " + secondName;
    }
}

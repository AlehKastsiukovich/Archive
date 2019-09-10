package by.etc.part6.archive;


import java.io.Serializable;


public class Student implements Serializable {
    private String name;
    private int course;
    private int age;
    private int id;

    public Student(String name, int course, int age, int id) {
        this.name = name;
        this.course = course;
        this.age = age;
        this.id = id;
    }

    public Student() {
    }

    public String toString() {
        return name + " " + age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }
}

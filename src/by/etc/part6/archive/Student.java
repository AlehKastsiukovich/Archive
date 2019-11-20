package by.etc.part6.archive;


import java.io.Serializable;


public class Student implements Serializable {
    private String name;
    private int age;
    private int course;
    private int id;

    public Student(String name, int age, int course, int id) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.id = id;
    }

    public Student() {
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

    public int getAge() {
        return age;
    }

    public int getCourse() {
        return course;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name + "/" + "age: "+ age + "/" + "course: " +course +  "/" + "id: " + id;
    }
}

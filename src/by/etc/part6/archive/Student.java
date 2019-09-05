package by.etc.part6.archive;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


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

    public void sendData(Student student) {
        try {
            Socket socket = new Socket("127.0.0.1", 8000);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(student);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Socket socket, Student student) {
        System.out.println("Choose your request: /MyData/ShowAllStudents/MakeChanges");

        Message.sendMessage(socket);

        sendData(this);
    }

    public static void main(String[] args) {
        Student student = new Student("Aleh","Kastsiukovich","meh", 4, 22);

        //student.sendData(student);
        try {
            Socket socket = new Socket("127.0.0.1", 8000);
            student.sendRequest(socket, student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

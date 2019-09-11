package by.etc.part6.archive;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


public class Client {

    public void sendData(Socket socket, Student student) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(student);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Socket socket) {
        System.out.println("CHOOSE OPTION: studentinfo/makechanges/createnew");
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        Message.sendMessage(socket, message);

        String getServerMessage = Message.receiveMessage(socket);
        System.out.println(getServerMessage);

        if (getServerMessage.equals("EnterName:")) {
            getStudentInfo(socket);

        } else if (getServerMessage.equals("AddNew:")) {
            addNewStudent(socket);

        } else if (getServerMessage.equals("ShowAll:")) {
            showAllStudents(socket);

        } else if (getServerMessage.equals("MakeChanges:")) {
            changeStudentData(socket);

        } else {
            System.out.println("Wrong enter.");
        }
    }

    public void changeStudentData(Socket socket) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of student which info will be changed:");
        String studName = scanner.nextLine();
        Message.sendMessage(socket, studName);
        System.out.println("Student data to be changed: " + Message.receiveMessage(socket));

        addNewStudent(socket);

    }

    public void showAllStudents(Socket socket) {
        List<Student> list = null;

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try {
                list = (List<Student>) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list != null) {
            System.out.println("Student list: ");
            for (Student student: list) {
                System.out.println(student.toString());
            }
        } else {
            System.out.println("List is empty!");
        }
    }

    public void addNewStudent(Socket socket) {
        Student student = new Student();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name: ");
        student.setName(scanner.nextLine());
        System.out.println("Enter course: ");
        student.setCourse(scanner.nextInt());
        System.out.println("Enter age: ");
        student.setAge(scanner.nextInt());
        System.out.println("Enter id: ");
        student.setId(scanner.nextInt());

        sendData(socket, student);
    }

    public void getStudentInfo(Socket socket) {
        Scanner scanner = new Scanner(System.in);
        String studName = scanner.nextLine();
        Message.sendMessage(socket, studName);

        System.out.println("Info about student: " + Message.receiveMessage(socket));
    }


    public static void main(String[] args) {
        Student student = new Student("Aleh Kastsiukovich", 4, 22, 112233);
        Client client = new Client();

        try {
            Socket socket = new Socket("127.0.0.1", 8000);
            client.sendRequest(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

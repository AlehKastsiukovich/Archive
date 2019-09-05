package by.etc.part6.archive;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private List<Student> studentList;
    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            studentList = new ArrayList<>();
            System.out.println("Server was created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Student readStudent() {
        Student student = null;

        try {
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            student = (Student) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return student;
    }

    public void showList() {
        for (Student student: studentList) {
            System.out.println();
        }
    }

    public void processRequest(Socket socket) {

        if (Message.receiveMessage(socket).equals("MyData")) {
            Student student = readStudent();

            for(Student std: studentList) {
                if (std.getFirstName().equals(student.getFirstName())) {
                    System.out.println(std.toString());
                }
            }
        } else {
            System.out.println("fock u!");
        }
    }


    public static void main(String[] args) {
        Server server = new Server(8000);
        server.studentList.add(new Student("Aleh","Kastsiukovich","meh", 4, 22));
        //server.readStudent();
        try {
            Socket socket = server.serverSocket.accept();
            server.processRequest(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

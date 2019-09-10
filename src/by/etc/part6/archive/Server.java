package by.etc.part6.archive;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public void processRequest(Socket socket) {
        String receiveMessage = Message.receiveMessage(socket);

        if (receiveMessage.equals("studentinfo")) {
            sendStudentInfo(socket);

        } else if (receiveMessage.equals("makechanges")) {
            System.out.println("changes");

        } else if (receiveMessage.equals("createnew")) {
            addNewStudent(socket);

        } else if (receiveMessage.equals("showall")) {
            showAllStudents(socket);
        }
    }

    public void showAllStudents(Socket socket) {
        String message = Message.message("ShowAll:");
        Message.sendMessage(socket, message);

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(studentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewStudent(Socket socket) {
        String message = Message.message("AddNew:");
        Message.sendMessage(socket, message);

        Student recStudent = null;

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try {
                recStudent = (Student) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (recStudent != null) {
            studentList.add(recStudent);
            System.out.println("Student " +recStudent.getName() + " was added.");
        }
    }

    public void sendStudentInfo(Socket socket) {
        String message = Message.message("EnterName:");
        Message.sendMessage(socket, message);
        String studentName = Message.receiveMessage(socket);

        Student studenttoSend = findStudentByName(studentList, studentName);

        if (studenttoSend!=null) {
            Message.sendMessage(socket, studenttoSend.toString());
        } else {
            System.out.println("student does not exist.");
        }
    }

    public Student findStudentByName(List<Student> students, String name) {
            for (Student student: studentList) {
                if (student.getName().equals(name)) {
                    System.out.println(student.toString());
                    return student;
                }
            }
            return null;
    }


    public static void main(String[] args) {

            try {
                Server server = new Server(8000);
                server.studentList.add(new Student("Aleh", 4, 31, 322));
                Socket socket = server.serverSocket.accept();
                server.processRequest(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

package by.etc.part6.archive;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private static  List<Student> students = new ArrayList<>();
    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server was started!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Student readStudent(Socket socket) {
        Student student = null;

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            student = (Student) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return student;
    }

    public void processRequest(Socket socket) {
        String receiveMessage = Message.receiveMessage(socket);
        try {
            if (receiveMessage.equals("studentinfo")) {
                sendStudentInfo(socket);

            } else if (receiveMessage.equals("makechanges")) {
                changeStudentData(socket);

            } else if (receiveMessage.equals("createnew")) {
                addNewStudent(socket);

            } else if (receiveMessage.equals("showall")) {
                showAllStudents(socket);
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void changeStudentData(Socket socket) {
        String message = Message.message("MakeChanges:");
        Message.sendMessage(socket, message);
        String studentName = Message.receiveMessage(socket);

            for (Student student: students) {

                if (student.getName().equals(studentName)) {
                    Message.sendMessage(socket, student.toString());
                    System.out.println("Student info before changes:");
                    System.out.println(student.toString());

                    student = readStudent(socket);
                    XmlArchive.writeToXml(students);

                    System.out.println("Student info after changes:");
                    System.out.println(student.toString());
                } else {
                    System.out.println("Student does not exist.");
                }
            }
    }

    public void showAllStudents(Socket socket) {
        String message = Message.message("ShowAll:");
        Message.sendMessage(socket, message);

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(students);
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
            students.add(recStudent);
            XmlArchive.writeToXml(students);
            System.out.println("Student " +recStudent.getName() + " was added.");
        }
    }

    public void sendStudentInfo(Socket socket) {
        String message = Message.message("EnterName:");
        Message.sendMessage(socket, message);
        String studentName = Message.receiveMessage(socket);

        Student studenttoSend = findStudentByName(students, studentName);

        if (studenttoSend!=null) {
            Message.sendMessage(socket, studenttoSend.toString());
        } else {
            System.out.println("Student does not exist.");
        }
    }

    public Student findStudentByName(List<Student> students, String name) {
            for (Student student: Server.students) {
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

                if (XmlArchive.getFILE().exists()) {
                   students = XmlArchive.readFromXml();
                }

                Socket socket = server.serverSocket.accept();
                server.processRequest(socket);
           } catch (IOException e) {
                e.printStackTrace();
           }
    }
}

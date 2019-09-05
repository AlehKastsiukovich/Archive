package by.etc.part6.archive;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

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
        sendData(student);
    }



    public static void main(String[] args) {
        Student student = new Student("Aleh","Kastsiukovich","meh", 4, 22);
        Client client = new Client();

        //student.sendData(student);
        try {
            Socket socket = new Socket("127.0.0.1", 8000);
            client.sendRequest(socket, student);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

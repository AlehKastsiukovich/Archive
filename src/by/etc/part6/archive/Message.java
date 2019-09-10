package by.etc.part6.archive;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Message {

    /*
    public static String message() {
        String message = null;
        Scanner scanner = new Scanner(System.in);
        message = scanner.nextLine();

        return message;
    }
     */

    public static String message(String mes) {
        String message = mes;
        return message;
    }

    public static String receiveMessage(Socket socket) {
        String message = null;

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            message = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    public static void sendMessage(Socket socket, String message) {
        try {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

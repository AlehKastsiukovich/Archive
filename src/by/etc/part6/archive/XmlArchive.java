package by.etc.part6.archive;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.List;


public class XmlArchive {
    private final static File FILE = new File("students.xml");

    public static File getFILE() {
        return FILE;
    }

    public static void writeToXml(List<Student> students) {
        try {
            FileOutputStream out = new FileOutputStream(FILE);
            XMLEncoder encoder = new XMLEncoder(out);
            encoder.writeObject(students);

            encoder.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> readFromXml() {
            try {
                FileInputStream fis = new FileInputStream(FILE);
                XMLDecoder decoder = new XMLDecoder(fis);

                List<Student> list = (List<Student>) decoder.readObject();

                decoder.close();
                fis.close();

                if (list != null) {
                    return list;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}

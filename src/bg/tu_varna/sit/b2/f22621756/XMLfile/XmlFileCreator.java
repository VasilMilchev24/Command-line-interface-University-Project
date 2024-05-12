package bg.tu_varna.sit.b2.f22621756.XMLfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class XmlFileCreator {
    public static void createXmlFile(String filePath) {
        File file = new File(filePath);
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<calendar>\n");
            writer.write("</calendar>\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Грешка: Неуспешно създаване на XML файл.");
        }
    }
}

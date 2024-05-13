package bg.tu_varna.sit.b2.f22621756.XMLfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlFileCreator {
    public static void createXmlFile(String name, String note, String date, String startTime, String endTime) {
        try {
            File xmlFile = new File("event.xml");
            FileWriter writer = new FileWriter(xmlFile);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<event>\n");
            writer.write("\t<name>" + name + "</name>\n");
            writer.write("\t<note>" + note + "</note>\n");
            writer.write("\t<date>" + date + "</date>\n");
            writer.write("\t<starttime>" + startTime + "</starttime>\n");
            writer.write("\t<endtime>" + endTime + "</endtime>\n");
            writer.write("</event>");
            writer.close();
            System.out.println("Създаден е XML файл със следните данни: ");
            System.out.println("Име на събитието: " + name);
            System.out.println("Коментар: " + note);
            System.out.println("Дата: " + date);
            System.out.println("Начален час: " + startTime);
            System.out.println("Краен час: " + endTime);
            System.out.println("Името на XML файла е event.xml");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Грешка при създаване на XML файл: " + e.getMessage());
        }
    }
}

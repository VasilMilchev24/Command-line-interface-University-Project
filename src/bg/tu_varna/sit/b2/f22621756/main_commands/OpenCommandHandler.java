package bg.tu_varna.sit.b2.f22621756.main_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class OpenCommandHandler implements CommandHandler {
    private CommandHandler successor;
    private boolean fileOpened;
    private File currentFile;

    @Override
    public String handleCommand(String command) {
        if (command.startsWith("open")) {
            String[] tokens = command.split(" ");
            if (tokens.length != 2) {
                System.out.println("Грешка: Невалиден синтаксис за командата open.");
                return;
            }

            String filePath = tokens[1];
            File file = new File(filePath);
            if (file.exists()) {
                // Ако файлът съществува, прочетете го и го затворете
                currentFile = file;
                readFromFile(file);
                fileOpened = true;
                System.out.println("Успешно отваряне на " + file.getName() + ".");
            } else {
                // Ако файла не съществува, създайте нов файл
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        System.out.println("Създаден нов файл: " + file.getName());
                        fileOpened = true;
                        currentFile = file;
                    } else {
                        System.out.println("Грешка: Не може да се създаде нов файл.");
                    }
                } catch (IOException e) {
                    System.out.println("Грешка при създаване на нов файл: " + e.getMessage());
                }
            }
        } else {
            if (successor != null) {
                successor.handleCommand(command);
            }
        }
    }

    @Override
    public void setSuccessor(CommandHandler successor) {
        this.successor = successor;
    }

    public void setFileOpened(boolean fileOpened) {
        this.fileOpened = fileOpened;
    }

    public boolean isFileOpened() {
        return fileOpened;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    private void readFromFile(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("event");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String note = element.getElementsByTagName("note").item(0).getTextContent();
                    String date = element.getElementsByTagName("date").item(0).getTextContent();
                    String startTime = element.getElementsByTagName("starttime").item(0).getTextContent();
                    String endTime = element.getElementsByTagName("endtime").item(0).getTextContent();

                    // Тук можете да използвате получените данни за създаване на обект или извършване на друга логика
                    System.out.println("Име на събитие: " + name);
                    System.out.println("Коментар: " + note);
                    System.out.println("Дата: " + date);
                    System.out.println("Начален час: " + startTime);
                    System.out.println("Краен час: " + endTime);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
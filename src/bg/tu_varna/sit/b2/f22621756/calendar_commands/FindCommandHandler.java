package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class FindCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("find")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 2) {
                String searchString = parts[1];
                findEvents(searchString);
            } else {
                System.out.println("Грешка: Невалиден формат на командата за find.");
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

    private void findEvents(String searchString) {
        try {
            File file = new File("calendar.xml");
            if (!file.exists()) {
                System.out.println("Грешка: Календарът все още не е създаден.");
                return;
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList events = doc.getElementsByTagName("event");
            for (int i = 0; i < events.getLength(); i++) {
                Node eventNode = events.item(i);
                if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element event = (Element) eventNode;
                    String eventName = event.getElementsByTagName("name").item(0).getTextContent();
                    String eventNote = event.getElementsByTagName("note").item(0).getTextContent();

                    if (eventName.contains(searchString) || eventNote.contains(searchString)) {
                        System.out.println("Намерено събитие: ");
                        System.out.println("Име: " + eventName);
                        System.out.println("Бележка: " + eventNote);
                        System.out.println();
                    }
                }
            }
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException e) {
            e.printStackTrace();
            System.out.println("Грешка при намиране на събития.");
        }
    }
}

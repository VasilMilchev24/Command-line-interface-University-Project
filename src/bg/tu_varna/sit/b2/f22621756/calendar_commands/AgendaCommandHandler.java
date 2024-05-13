package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class AgendaCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("agenda")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 2) {
                String date = parts[1];
                showAgendaForDate(date);
            } else {
                System.out.println("Грешка: Невалиден формат на командата за agenda.");
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

    private void showAgendaForDate(String date) {
        try {
            File file = new File("calendar.xml");
            if (!file.exists()) {
                System.out.println("Грешка: Календарът все още не е създаден.");
                return;
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date targetDate = dateFormat.parse(date);

            NodeList events = doc.getElementsByTagName("event");
            for (int i = 0; i < events.getLength(); i++) {
                Node eventNode = events.item(i);
                if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element event = (Element) eventNode;
                    String eventDateStr = event.getElementsByTagName("date").item(0).getTextContent();
                    Date eventDate = dateFormat.parse(eventDateStr);

                    if (eventDate.equals(targetDate)) {
                        String startTime = event.getElementsByTagName("starttime").item(0).getTextContent();
                        String endTime = event.getElementsByTagName("endtime").item(0).getTextContent();
                        String eventName = event.getElementsByTagName("name").item(0).getTextContent();
                        String eventNote = event.getElementsByTagName("note").item(0).getTextContent();

                        System.out.println("Дата: " + eventDateStr);
                        System.out.println("Време: от " + startTime + " до " + endTime);
                        System.out.println("Събитие: " + eventName);
                        System.out.println("Коментар: " + eventNote);
                        System.out.println();
                    }
                }
            }
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException | ParseException e) {
            e.printStackTrace();
            System.out.println("Грешка при извеждане на агенда за деня.");
        }
    }
}

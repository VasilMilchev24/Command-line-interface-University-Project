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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("change")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 5) {
                String date = parts[1];
                String startTime = parts[2];
                String option = parts[3];
                String newValue = parts[4];
                changeEvent(date, startTime, option, newValue);
            } else {
                System.out.println("Грешка: Невалиден формат на командата за change.");
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

    private void changeEvent(String date, String startTime, String option, String newValue) {
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
                    String eventDateStr = event.getElementsByTagName("date").item(0).getTextContent();
                    String eventStartTime = event.getElementsByTagName("starttime").item(0).getTextContent();

                    if (eventDateStr.equals(date) && eventStartTime.equals(startTime)) {
                        Element optionElement = (Element) event.getElementsByTagName(option).item(0);
                        if (optionElement != null) {
                            optionElement.setTextContent(newValue);
                            saveChangesToFile(doc, file);
                            System.out.println("Успешно променено събитие.");
                            return;
                        } else {
                            System.out.println("Грешка: Невалидна опция за промяна.");
                            return;
                        }
                    }
                }
            }
            System.out.println("Грешка: Събитие с дата " + date + " и начален час " + startTime + " не е намерено.");
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException | ParseException | TransformerException e) {
            e.printStackTrace();
            System.out.println("Грешка при промяна на събитие.");
        }
    }

    private void saveChangesToFile(Document doc, File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }
}


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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class UnbookCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("unbook")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 4) {
                String date = parts[1];
                String startTime = parts[2];
                String endTime = parts[3];
                unbookAppointment(date, startTime, endTime);
            } else {
                System.out.println("Грешка: Невалиден формат на командата за unbook.");
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

    private void unbookAppointment(String date, String startTime, String endTime) {
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
                    String eventDate = event.getElementsByTagName("date").item(0).getTextContent();
                    String eventStartTime = event.getElementsByTagName("starttime").item(0).getTextContent();
                    String eventEndTime = event.getElementsByTagName("endtime").item(0).getTextContent();

                    if (eventDate.equals(date) && eventStartTime.equals(startTime) && eventEndTime.equals(endTime)) {
                        eventNode.getParentNode().removeChild(eventNode);
                        System.out.println("Срещата на " + date + " от " + startTime + " до " + endTime + " беше успешно отменена.");
                    }
                }
            }

            // Записване на промените обратно във файла
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | TransformerException e) {
            e.printStackTrace();
            System.out.println("Грешка при отменяне на срещата в календара.");
        }
    }
}

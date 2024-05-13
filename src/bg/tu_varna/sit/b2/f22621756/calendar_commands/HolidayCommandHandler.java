package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HolidayCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("holiday")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 2) {
                String date = parts[1];
                markAsHoliday(date);
            } else {
                System.out.println("Грешка: Невалиден формат на командата за holiday.");
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

    private void markAsHoliday(String date) {
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
                Element event = (Element) events.item(i);
                String eventDate = event.getElementsByTagName("date").item(0).getTextContent();
                if (eventDate.equals(date)) {
                    event.setAttribute("holiday", "true");
                    System.out.println("Датата " + date + " е отбелязана като неработна.");
                    saveChanges(doc, file);
                    return;
                }
            }

            System.out.println("Грешка: Няма събитие за датата " + date);
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException | TransformerException e) {
            e.printStackTrace();
            System.out.println("Грешка при отбелязване на дата като неработна.");
        }
    }

    private void saveChanges(Document doc, File file) throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);
        }
    }
}

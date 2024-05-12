package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public class BookCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("book")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 6) {
                String date = parts[1];
                String startTime = parts[2];
                String endTime = parts[3];
                String name = parts[4];
                String note = parts[5];
                bookAppointment(date, startTime, endTime, name, note);
            } else {
                System.out.println("Грешка: Невалиден формат на командата за book.");
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

    private void bookAppointment(String date, String startTime, String endTime, String name, String note) {
        try {
            // Проверка и създаване на XML файл, ако не съществува
            File file = new File("calendar.xml");
            if (!file.exists()) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("calendar");
                doc.appendChild(rootElement);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            }

            // Четене на съществуващия XML файл
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            // Създаване на новия елемент за събитието
            Element event = doc.createElement("event");
            Element eventDate = doc.createElement("date");
            eventDate.appendChild(doc.createTextNode(date));
            event.appendChild(eventDate);

            Element eventStartTime = doc.createElement("starttime");
            eventStartTime.appendChild(doc.createTextNode(startTime));
            event.appendChild(eventStartTime);

            Element eventEndTime = doc.createElement("endtime");
            eventEndTime.appendChild(doc.createTextNode(endTime));
            event.appendChild(eventEndTime);

            Element eventName = doc.createElement("name");
            eventName.appendChild(doc.createTextNode(name));
            event.appendChild(eventName);

            Element eventNote = doc.createElement("note");
            eventNote.appendChild(doc.createTextNode(note));
            event.appendChild(eventNote);

            // Добавяне на събитието към кореновия елемент на файла
            doc.getDocumentElement().appendChild(event);

            // Записване на промените обратно във файла
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            System.out.println("Събитието е успешно запазено в календара.");
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | TransformerException e) {
            e.printStackTrace();
            System.out.println("Грешка при записване на събитието в календара.");
        }
    }
}

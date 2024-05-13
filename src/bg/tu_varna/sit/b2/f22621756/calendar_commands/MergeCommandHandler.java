package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.util.*;

public class MergeCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("merge")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 2) {
                String externalCalendarFile = parts[1];
                mergeCalendars(externalCalendarFile);
            } else {
                System.out.println("Грешка: Невалиден формат на командата за merge.");
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

    private void mergeCalendars(String externalCalendarFile) {
        try {
            File currentCalendarFile = new File("calendar.xml");
            File externalFile = new File(externalCalendarFile);
            if (!currentCalendarFile.exists() || !externalFile.exists()) {
                System.out.println("Грешка: Календарът все още не е създаден.");
                return;
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document currentCalendarDoc = docBuilder.parse(currentCalendarFile);
            Document externalCalendarDoc = docBuilder.parse(externalFile);

            Set<Event> currentEvents = getEvents(currentCalendarDoc);
            Set<Event> externalEvents = getEvents(externalCalendarDoc);

            Set<Event> mergedEvents = new HashSet<>();
            mergedEvents.addAll(currentEvents);
            mergedEvents.addAll(externalEvents);

            // Проверка за конфликти и решаване на тези конфликти
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (Event event : externalEvents) {
                if (currentEvents.contains(event)) {
                    System.out.println("Конфликт: " + event.getName() + " на " + dateFormat.format(event.getStartDate()) +
                            ". Изберете опция: 1 - запази текущото събитие, 2 - запази новото събитие.");
                    int choice = scanner.nextInt();
                    if (choice == 2) {
                        mergedEvents.remove(event); // Премахване на текущото събитие
                        mergedEvents.add(event); // Добавяне на новото събитие
                    }
                }
            }

            // Записване на обединения календар във файл
            saveMergedCalendar(currentCalendarFile, mergedEvents);
            System.out.println("Обединението на календарите е успешно.");
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException | TransformerException | ParseException e) {
            e.printStackTrace();
            System.out.println("Грешка при обединяване на календарите.");
        }
    }

    private Set<Event> getEvents(Document doc) throws ParseException {
        Set<Event> events = new HashSet<>();
        NodeList eventNodes = doc.getElementsByTagName("event");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (int i = 0; i < eventNodes.getLength(); i++) {
            Element eventElement = (Element) eventNodes.item(i);
            String name = eventElement.getElementsByTagName("name").item(0).getTextContent();
            String dateString = eventElement.getElementsByTagName("date").item(0).getTextContent();
            String startTimeString = eventElement.getElementsByTagName("starttime").item(0).getTextContent();
            Date startDate = dateFormat.parse(dateString + " " + startTimeString);
            events.add(new Event(name, startDate));
        }
        return events;
    }

    private void saveMergedCalendar(File calendarFile, Set<Event> events) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("calendar");
        doc.appendChild(rootElement);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (Event event : events) {
            Element eventElement = doc.createElement("event");
            rootElement.appendChild(eventElement);

            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(event.getName()));
            eventElement.appendChild(nameElement);

            Element dateElement = doc.createElement("date");
            dateElement.appendChild(doc.createTextNode(dateFormat.format(event.getStartDate())));
            eventElement.appendChild(dateElement);

            Element startTimeElement = doc.createElement("starttime");
            startTimeElement.appendChild(doc.createTextNode(timeFormat.format(event.getStartDate())));
            eventElement.appendChild(startTimeElement);
        }

        // Записване на XML документа във файл
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(calendarFile);
        transformer.transform(source, result);
    }
}

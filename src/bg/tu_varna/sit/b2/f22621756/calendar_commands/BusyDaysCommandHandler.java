package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BusyDaysCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("busydays")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 3) {
                String fromDateStr = parts[1];
                String toDateStr = parts[2];
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fromDate = dateFormat.parse(fromDateStr);
                    Date toDate = dateFormat.parse(toDateStr);
                    showBusyDays(fromDate, toDate);
                } catch (ParseException e) {
                    System.out.println("Грешка: Невалиден формат на датите.");
                }
            } else {
                System.out.println("Грешка: Невалиден формат на командата за busydays.");
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

    private void showBusyDays(Date fromDate, Date toDate) {
        try {
            File file = new File("calendar.xml");
            if (!file.exists()) {
                System.out.println("Грешка: Календарът все още не е създаден.");
                return;
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            Map<Date, Integer> busyDaysMap = new HashMap<>();

            NodeList events = doc.getElementsByTagName("event");
            for (int i = 0; i < events.getLength(); i++) {
                Element event = (Element) events.item(i);
                String dateString = event.getElementsByTagName("date").item(0).getTextContent();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date eventDate = dateFormat.parse(dateString);
                if (eventDate.compareTo(fromDate) >= 0 && eventDate.compareTo(toDate) <= 0) {
                    busyDaysMap.put(eventDate, busyDaysMap.getOrDefault(eventDate, 0) + 1);
                }
            }

            List<Map.Entry<Date, Integer>> sortedList = new ArrayList<>(busyDaysMap.entrySet());
            sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            System.out.println("Статистика за натовареност в периода от " + fromDate + " до " + toDate + ":");
            for (Map.Entry<Date, Integer> entry : sortedList) {
                System.out.println(entry.getKey() + " - " + entry.getValue() + " часа");
            }
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException | ParseException e) {
            e.printStackTrace();
            System.out.println("Грешка при извличане на статистика за натовареност.");
        }
    }
}


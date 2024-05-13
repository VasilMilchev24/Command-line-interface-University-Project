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

public class FindSlotCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("findslot")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 3) {
                String fromDateStr = parts[1];
                int hours = Integer.parseInt(parts[2]);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fromDate = dateFormat.parse(fromDateStr);
                    findAvailableSlot(fromDate, hours);
                } catch (ParseException e) {
                    System.out.println("Грешка: Невалиден формат на датата.");
                }
            } else {
                System.out.println("Грешка: Невалиден формат на командата за findslot.");
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

    private void findAvailableSlot(Date fromDate, int hours) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromDate);

            File file = new File("calendar.xml");
            if (!file.exists()) {
                System.out.println("Грешка: Календарът все още не е създаден.");
                return;
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            Set<Date> busySlots = new HashSet<>();
            NodeList events = doc.getElementsByTagName("event");
            for (int i = 0; i < events.getLength(); i++) {
                Element event = (Element) events.item(i);
                String dateString = event.getElementsByTagName("date").item(0).getTextContent();
                String startTimeString = event.getElementsByTagName("starttime").item(0).getTextContent();
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date eventDateTime = dateTimeFormat.parse(dateString + " " + startTimeString);
                calendar.setTime(eventDateTime);
                calendar.add(Calendar.HOUR_OF_DAY, hours);
                busySlots.add(calendar.getTime());
            }

            calendar.setTime(fromDate);
            while (!isWorkingDay(calendar)) {
                calendar.add(Calendar.DATE, 1);
            }

            int availableSlotsCount = 0;
            while (availableSlotsCount < 5) {
                if (!busySlots.contains(calendar.getTime())) {
                    System.out.println("Свободно време за среща на: " + calendar.getTime());
                    availableSlotsCount++;
                }
                calendar.add(Calendar.DATE, 1);
                while (!isWorkingDay(calendar)) {
                    calendar.add(Calendar.DATE, 1);
                }
            }
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException | ParseException e) {
            e.printStackTrace();
            System.out.println("Грешка при намиране на свободно време за среща.");
        }
    }

    private boolean isWorkingDay(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }
}

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FindSlotWithCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("findslotwith")) {
            String[] parts = command.split("\\s+");
            if (parts.length == 4) {
                String fromDateStr = parts[1];
                int hours = Integer.parseInt(parts[2]);
                String calendarFile = parts[3];
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fromDate = dateFormat.parse(fromDateStr);
                    findAvailableSlot(fromDate, hours, calendarFile);
                } catch (ParseException e) {
                    System.out.println("Грешка: Невалиден формат на датата.");
                }
            } else {
                System.out.println("Грешка: Невалиден формат на командата за findslotwith.");
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

    private void findAvailableSlot(Date fromDate, int hours, String calendarFile) {
        try {
            File currentCalendarFile = new File("calendar.xml");
            File externalCalendarFile = new File(calendarFile);
            if (!currentCalendarFile.exists() || !externalCalendarFile.exists()) {
                System.out.println("Грешка: Календарът все още не е създаден.");
                return;
            }

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document currentCalendarDoc = docBuilder.parse(currentCalendarFile);
            Document externalCalendarDoc = docBuilder.parse(externalCalendarFile);

            Set<Date> currentBusySlots = getBusySlots(currentCalendarDoc);
            Set<Date> externalBusySlots = getBusySlots(externalCalendarDoc);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromDate);
            calendar.set(Calendar.HOUR_OF_DAY, 8); // Начален час на работния ден
            calendar.set(Calendar.MINUTE, 0);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            boolean foundSlot = false;
            while (!foundSlot) {
                if (!currentBusySlots.contains(calendar.getTime()) && !externalBusySlots.contains(calendar.getTime())) {
                    Date endDate = calculateEndDate(calendar.getTime(), hours);
                    boolean isWorkingHours = calendar.get(Calendar.HOUR_OF_DAY) >= 8 && calendar.get(Calendar.HOUR_OF_DAY) < 17;
                    if (!currentBusySlots.contains(endDate) && !externalBusySlots.contains(endDate) && isWorkingHours) {
                        System.out.println("Свободно време за среща на: " + dateFormat.format(calendar.getTime()) +
                                " от " + timeFormat.format(calendar.getTime()) + " до " + timeFormat.format(endDate));
                        foundSlot = true;
                    }
                }
                calendar.add(Calendar.MINUTE, 15); // Преминаваме към следващата 15-минутна итерация
                if (calendar.get(Calendar.HOUR_OF_DAY) >= 17) {
                    calendar.add(Calendar.DATE, 1); // Прехвърляме се на следващия работен ден
                    calendar.set(Calendar.HOUR_OF_DAY, 8); // Започваме от началото на работния ден
                    calendar.set(Calendar.MINUTE, 0);
                }
            }
        } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException e) {
            e.printStackTrace();
            System.out.println("Грешка при намиране на свободно време за среща.");
        }
    }

    private Set<Date> getBusySlots(Document doc) {
        Set<Date> busySlots = new HashSet<>();
        NodeList events = doc.getElementsByTagName("event");
        for (int i = 0; i < events.getLength(); i++) {
            Element event = (Element) events.item(i);
            String dateString = event.getElementsByTagName("date").item(0).getTextContent();
            String startTimeString = event.getElementsByTagName("starttime").item(0).getTextContent();
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date eventDateTime = dateTimeFormat.parse(dateString + " " + startTimeString);
                busySlots.add(eventDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return busySlots;
    }

    private Date calculateEndDate(Date startDate, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}

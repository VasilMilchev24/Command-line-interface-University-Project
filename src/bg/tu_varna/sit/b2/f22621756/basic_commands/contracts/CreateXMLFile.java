package bg.tu_varna.sit.b2.f22621756.basic_commands.contracts;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
public class CreateXMLFile {
    public void generateXML() {
        // Create Scanner object to read input from keyboard
        Scanner scanner = new Scanner(System.in);

        // Accept user input for XML data
        System.out.print("Enter event's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter note: ");
        String note = scanner.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter start time (HH:MM): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:MM): ");
        String endTime = scanner.nextLine();

        // Generate XML content using user input
        String xmlContent = generateXMLContent(name, note, date, startTime, endTime);

        // Specify file path
        String filePath = "calendar.xml";

        // Create XML file
        createXMLFile(xmlContent, filePath);

        // Close the scanner
        scanner.close();
    }

    private String generateXMLContent(String name, String note, String date, String startTime, String endTime) {
        // Generate XML content using provided data
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "    <event>\n" +
                "        <name>" + name + "</name>\n" +
                "        <note>" + note + "</note>\n" +
                "        <date>" + date + "</date>\n" +
                "        <starttime>" + startTime + "</starttime>\n" +
                "        <endtime>" + endTime + "</endtime>\n" +
                "    </event>\n" +
                "</root>";
    }

    private void createXMLFile(String content, String filePath) {
        try {
            // Create file object
            File file = new File(filePath);

            // Create FileWriter object
            FileWriter writer = new FileWriter(file);

            // Write content to the file
            writer.write(content);

            // Close FileWriter
            writer.close();

            System.out.println("XML file created successfully at: " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while creating the XML file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
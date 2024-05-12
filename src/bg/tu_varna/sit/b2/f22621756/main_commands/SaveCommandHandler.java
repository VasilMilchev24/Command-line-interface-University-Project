package bg.tu_varna.sit.b2.f22621756.main_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveCommandHandler  implements CommandHandler {
    private CommandHandler successor;
    private OpenCommandHandler openCommandHandler;
    private File lastOpenedFile;

    public SaveCommandHandler(OpenCommandHandler openCommandHandler) {
        this.openCommandHandler = openCommandHandler;
    }

    @Override
    public void handleCommand(String command) {
        if (command.equals("save")) {
            if (openCommandHandler.isFileOpened()) {
                saveToFile(openCommandHandler.getCurrentFile());
            } else {
                System.out.println("Грешка: Няма отворен файл за запис.");
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

    public void setLastOpenedFile(File lastOpenedFile) {
        this.lastOpenedFile = lastOpenedFile;
    }

    private void saveToFile(File file) {
        try (FileWriter writer = new FileWriter(lastOpenedFile)) {
            List<String> dataToWrite = prepareDataForWritingFromConsole(); // Получаване на данните от конзолата

            // Записване на всеки ред от данните във файла
            for (String line : dataToWrite) {
                writer.write(line + "\n");
            }
            System.out.println("Успешно запазване на " + lastOpenedFile.getName() + ".");
        } catch (IOException e) {
            System.out.println("Грешка при записване на файл: " + e.getMessage());
        }
    }

    private List<String> prepareDataForWritingFromConsole() {
        List<String> dataToWrite = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Въвеждане на данните от потребителя
        System.out.println("Въведете име на събитието:");
        String name = scanner.nextLine();
        System.out.println("Въведете коментар за събитието:");
        String note = scanner.nextLine();
        System.out.println("Въведете дата на събитието (във формат YYYY-MM-DD):");
        String date = scanner.nextLine();
        System.out.println("Въведете начален час на събитието (във формат HH:mm):");
        String startTime = scanner.nextLine();
        System.out.println("Въведете краен час на събитието (във формат HH:mm):");
        String endTime = scanner.nextLine();

        // Форматиране на данните в XML формат
        dataToWrite.add("<event>");
        dataToWrite.add("  <name>" + name + "</name>");
        dataToWrite.add("  <note>" + note + "</note>");
        dataToWrite.add("  <date>" + date + "</date>");
        dataToWrite.add("  <starttime>" + startTime + "</starttime>");
        dataToWrite.add("  <endtime>" + endTime + "</endtime>");
        dataToWrite.add("</event>");

        return dataToWrite;
    }
}

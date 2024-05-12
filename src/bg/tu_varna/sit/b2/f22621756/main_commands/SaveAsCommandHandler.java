package bg.tu_varna.sit.b2.f22621756.main_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveAsCommandHandler implements CommandHandler {
    private CommandHandler successor;

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("saveas")) {
            String[] parts = command.split("\\s+", 2); // Разделяне на командата на две части: "saveas" и пътят към файла
            if (parts.length == 2) {
                String filePath = parts[1].replaceAll("\"", ""); // Извличане на пътя към файла
                saveToFile(filePath); // Запазване на данните в указания файл
            } else {
                System.out.println("Грешка: Невалиден формат на командата за saveas.");
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

    private void saveToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            List<String> dataToWrite = prepareDataForWritingFromConsole(); // Получаване на данните за записване

            // Записване на всеки ред от данните във файла
            for (String line : dataToWrite) {
                writer.write(line + "\n");
            }
            System.out.println("Успешно запазване в " + filePath);
        } catch (IOException e) {
            System.out.println("Грешка при записване на файл: " + e.getMessage());
        }
    }

    private List<String> prepareDataForWritingFromConsole() {
        // Логика за получаване на данните от конзолата и форматиране на XML
        // Можете да използвате логиката от SaveCommandHandler или да я реализирате отново тук
    }
}

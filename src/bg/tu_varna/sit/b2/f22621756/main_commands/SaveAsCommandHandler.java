package bg.tu_varna.sit.b2.f22621756.main_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveAsCommandHandler implements CommandHandler {
    private File currentFile;
    private CommandHandler nextHandler;

    @Override
    public String handleCommand(String command) {
        if (command.startsWith("saveas")) {
            String[] parts = command.split(" ");
            if (parts.length != 2) {
                return "Невалиден формат на командата. Форматът трябва да бъде: saveas \"път_към_файл\"";
            }
            String filePath = parts[1].replaceAll("\"", ""); // Премахваме кавичките около пътя
            return saveToFile(filePath);
        } else {
            if (nextHandler != null) {
                return nextHandler.handleCommand(command);
            } else {
                return "Не може да се обработи командата: " + command;
            }
        }
    }

    private String saveToFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write("Промените са записани успешно.");
            writer.close();
            return "Успешно запазихте промените във файл: " + filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Грешка при записване на файл: " + e.getMessage();
        }
    }

    @Override
    public void setSuccessor(CommandHandler successor) {
        this.nextHandler = successor;
    }

    public void setCurrentFile(File file) {
        this.currentFile = file;
    }

    public File getCurrentFile() {
        return currentFile;
    }
}

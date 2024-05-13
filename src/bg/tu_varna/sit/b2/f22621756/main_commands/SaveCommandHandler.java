package bg.tu_varna.sit.b2.f22621756.main_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveCommandHandler implements CommandHandler {
    private File currentFile;
    private CommandHandler nextHandler;

    @Override
    public String handleCommand(String command) {
        if (command.startsWith("save")) {
            return saveToFile();
        } else {
            if (nextHandler != null) {
                return nextHandler.handleCommand(command);
            } else {
                return "Не може да се обработи командата: " + command;
            }
        }
    }

    private String saveToFile() {
        if (currentFile != null) {
            try {
                FileWriter writer = new FileWriter(currentFile);
                writer.write("Промените са записани успешно.");
                writer.close();
                return "Успешно запазихте промените във файл: " + currentFile.getName();
            } catch (IOException e) {
                e.printStackTrace();
                return "Грешка при записване на файл.";
            }
        } else {
            return "Не е отворен файл за запис. Моля, отворете файл преди да използвате командата save.";
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

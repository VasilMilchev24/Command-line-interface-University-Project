package bg.tu_varna.sit.b2.f22621756.main_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CloseCommandHandler implements CommandHandler {
    private CommandHandler successor;
    private OpenCommandHandler openCommandHandler;
    private File currentFile;

    public CloseCommandHandler(OpenCommandHandler openCommandHandler) {
        this.openCommandHandler = openCommandHandler;
    }

    @Override
    public void handleCommand(String command) {
        if (command.equals("close")) {
            if (openCommandHandler.isFileOpened()) {
                // Затваряне на текущо отворения файл
                currentFile = openCommandHandler.getCurrentFile();
                openCommandHandler.setFileOpened(false);
                System.out.println("Успешно затваряне на " + currentFile.getName() + ".");
            } else {
                System.out.println("Грешка: Няма отворен файл за затваряне.");
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
}

package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

public class FindCommandHandler implements CommandHandler {
    private CommandHandler successor;
    private OpenCommandHandler openCommandHandler;

    public FindCommandHandler(OpenCommandHandler openCommandHandler) {
        this.openCommandHandler = openCommandHandler;
    }

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("find")) {
            if (openCommandHandler.isFileOpened()) {
                // Имплементация за търсене на събитие
                System.out.println("Резултати от търсенето:");
                // Изведете данните за намерените събития
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
}
package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

public class AgendaCommandHandler implements CommandHandler {
    private CommandHandler successor;
    private OpenCommandHandler openCommandHandler;

    public AgendaCommandHandler(OpenCommandHandler openCommandHandler) {
        this.openCommandHandler = openCommandHandler;
    }

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("agenda")) {
            if (openCommandHandler.isFileOpened()) {
                // Имплементация за извеждане на агенда за деня
                System.out.println("Агендата за деня е:");
                // Вземете данните от календара и ги изведете
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



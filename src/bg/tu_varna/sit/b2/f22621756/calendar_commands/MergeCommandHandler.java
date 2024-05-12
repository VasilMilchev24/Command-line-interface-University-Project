package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

public class MergeCommandHandler implements CommandHandler {
    private CommandHandler successor;
    private OpenCommandHandler openCommandHandler;

    public MergeCommandHandler(OpenCommandHandler openCommandHandler) {
        this.openCommandHandler = openCommandHandler;
    }

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("merge")) {
            if (openCommandHandler.isFileOpened()) {
                // Имплементация за сливане на събития от друг календар в текущия
                System.out.println("Сливане на събития от друг календар в текущия...");
                // Изпълнете сливането на събитията
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
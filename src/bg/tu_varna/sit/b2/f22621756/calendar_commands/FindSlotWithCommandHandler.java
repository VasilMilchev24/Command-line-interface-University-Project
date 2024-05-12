package bg.tu_varna.sit.b2.f22621756.calendar_commands;

import bg.tu_varna.sit.b2.f22621756.XMLfile.CommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;

public class FindSlotWithCommandHandler implements CommandHandler {
    private CommandHandler successor;
    private OpenCommandHandler openCommandHandler;

    public FindSlotWithCommandHandler(OpenCommandHandler openCommandHandler) {
        this.openCommandHandler = openCommandHandler;
    }

    @Override
    public void handleCommand(String command) {
        if (command.startsWith("findslotwith")) {
            if (openCommandHandler.isFileOpened()) {
                // Имплементация за намиране на свободно време за среща, синхронизирано с друг календар
                System.out.println("Свободно време за среща с друг календар е:");
                // Изведете подходящи дати и часове за среща, синхронизирани с другия календар
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

package bg.tu_varna.sit.b2.f22621756.XMLfile;

import bg.tu_varna.sit.b2.f22621756.calendar_commands.*;
import bg.tu_varna.sit.b2.f22621756.main_commands.CloseCommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.OpenCommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.SaveAsCommandHandler;
import bg.tu_varna.sit.b2.f22621756.main_commands.SaveCommandHandler;

import java.util.Scanner;

public class CalendarSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OpenCommandHandler openCommandHandler = new OpenCommandHandler();
        CommandHandler handlerChain = openCommandHandler;
        handlerChain.setSuccessor(new CloseCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new SaveCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new SaveAsCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new BookCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new UnbookCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new AgendaCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new ChangeCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new FindCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new HolidayCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new BusyDaysCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new FindSlotCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new FindSlotWithCommandHandler(openCommandHandler));
        handlerChain.setSuccessor(new MergeCommandHandler(openCommandHandler));
        // Добавете имплементации за останалите команди тук...

        while (true) {
            System.out.print("Въведете команда: ");
            String command = scanner.nextLine().trim();
            if (command.equals("exit")) {
                System.out.println("Излизане от програмата...");
                break;
            }
            handlerChain.handleCommand(command);
        }
        scanner.close();
    }
}

package bg.tu_varna.sit.b2.f22621756.basic_commands;

import bg.tu_varna.sit.b2.f22621756.basic_commands.contracts.BasicCommandsHandler;
import bg.tu_varna.sit.b2.f22621756.basic_commands.models.*;

import java.util.Scanner;

public class TestBasicCommandsApplication {
    private BasicCommandsHandler commandHandlerChain;

    public TestBasicCommandsApplication() {
        // Create the command handler chain
        OpenHandler openHandler = new OpenHandler();
        CloseHandler closeHandler = new CloseHandler(openHandler) {
            @Override
            public boolean isFileLoaded() {
                return false;
            }
        };
        SaveHandler saveHandler = new SaveHandler(openHandler) {
            @Override
            public boolean isFileLoaded() {
                return false;
            }
        };
        ExitHandler exitHandler = new ExitHandler() {
            @Override
            public boolean isFileLoaded() {
                return false;
            }
        };

        // Set up the chain of responsibility
        openHandler.setNextHandler(closeHandler);
        closeHandler.setNextHandler(saveHandler);
        saveHandler.setNextHandler(exitHandler);

        // Set the starting point of the chain
        commandHandlerChain = openHandler;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Welcome to the Basic Commands Application!");

        do {
            System.out.print("Enter command: ");
            input = scanner.nextLine();
            String output = commandHandlerChain.handle(input);
            if (output != null) {
                System.out.println(output);
            }
        } while (!input.equals("exit"));

        scanner.close();
    }

    public static void main(String[] args) {
        TestBasicCommandsApplication application = new TestBasicCommandsApplication();
        application.run();
    }
}
// Path: src/bg/tu_varna/sit/b2/f22621756/basic_commands/models/OpenHandler.java
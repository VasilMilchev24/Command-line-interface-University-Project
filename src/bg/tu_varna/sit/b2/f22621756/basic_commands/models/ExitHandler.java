package bg.tu_varna.sit.b2.f22621756.basic_commands.models;
import bg.tu_varna.sit.b2.f22621756.basic_commands.contracts.BasicCommandsHandler;
public abstract class ExitHandler implements BasicCommandsHandler{
    @Override
    public void setNextHandler(BasicCommandsHandler nextHandler) {
        // ExitHandler does not have a next handler
    }

    @Override
    public String handle(String input) {
        if (input.equals("exit")) {
            System.out.println("Exiting the program.");
            System.exit(0); // Exit the program
        } else {
            return "Invalid command: " + input;
        }
        return null; // This line will never be reached, but added to satisfy method signature
    }

}
package bg.tu_varna.sit.b2.f22621756.basic_commands.models;
import bg.tu_varna.sit.b2.f22621756.basic_commands.contracts.BasicCommandsHandler;
public abstract class HelpHandler implements BasicCommandsHandler{
    private BasicCommandsHandler nextHandler;
    @Override
    public void setNextHandler(BasicCommandsHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    @Override
    public String handle(String input) {
        if (input.equals("help")) {
            return "Available commands:\n" +
                    "open <file> - opens a file\n" +
                    "close - closes the currently opened file\n" +
                    "save - saves the currently opened file\n" +
                    "saveas <file> - saves the currently opened file in another file\n" +
                    "help - prints this help message\n" +
                    "exit - exits the program";
        } else if (nextHandler != null) {
            return nextHandler.handle(input);
        } else {
            return "Invalid command: " + input;
        }
    }
    @Override
    public boolean isFileLoaded() {
        return false;
    }
}
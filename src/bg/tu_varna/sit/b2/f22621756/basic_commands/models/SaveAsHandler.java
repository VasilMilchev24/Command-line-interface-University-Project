package bg.tu_varna.sit.b2.f22621756.basic_commands.models;
import bg.tu_varna.sit.b2.f22621756.basic_commands.contracts.BasicCommandsHandler;
import java.io.FileWriter;
import java.io.IOException;
public abstract class SaveAsHandler implements BasicCommandsHandler{
    private BasicCommandsHandler nextHandler;
    private OpenHandler openHandler;

    public SaveAsHandler(OpenHandler openHandler) {
        this.openHandler = openHandler;
    }

    @Override
    public void setNextHandler(BasicCommandsHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String handle(String input) {
        if (input.startsWith("saveas ")) {
            String fileLocation = input.substring(7); // Extract file location and name from input
            try {
                FileWriter writer = new FileWriter(fileLocation);
                writer.write(openHandler.getLoadedFileContent());
                writer.close();
                return "Successfully saved " + fileLocation;
            } catch (IOException e) {
                return "Failed to save file: " + fileLocation + ", Error: " + e.getMessage();
            }
        } else if (nextHandler != null) {
            return nextHandler.handle(input);
        } else {
            return "Invalid command: " + input;
        }
    }
}
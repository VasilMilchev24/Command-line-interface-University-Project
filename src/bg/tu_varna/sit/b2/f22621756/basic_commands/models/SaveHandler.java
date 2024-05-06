package bg.tu_varna.sit.b2.f22621756.basic_commands.models;

import bg.tu_varna.sit.b2.f22621756.basic_commands.contracts.BasicCommandsHandler;

import java.io.FileWriter;
import java.io.IOException;

public abstract class SaveHandler implements BasicCommandsHandler {

    private BasicCommandsHandler nextHandler;
    private OpenHandler openHandler;

    public SaveHandler(OpenHandler openHandler) {
        this.openHandler = openHandler;
    }

    @Override
    public void setNextHandler(BasicCommandsHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String handle(String input) {
        if (input.equals("save")) {
            if (openHandler.isFileLoaded()) {
                String fileContent = openHandler.getLoadedFileContent();
                String filePath = openHandler.getLoadedFilePath();

                try {
                    FileWriter writer = new FileWriter(filePath);
                    writer.write(fileContent);
                    writer.close();
                    return "Successfully saved " + filePath;
                } catch (IOException e) {
                    return "Failed to save file: " + filePath + ", Error: " + e.getMessage();
                }
            } else {
                return "No file is currently open.";
            }
        } else if (nextHandler != null) {
            return nextHandler.handle(input);
        } else {
            return "Invalid command: " + input;
        }
    }
}
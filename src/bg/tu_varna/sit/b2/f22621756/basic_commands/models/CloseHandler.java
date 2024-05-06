package bg.tu_varna.sit.b2.f22621756.basic_commands.models;

import bg.tu_varna.sit.b2.f22621756.basic_commands.contracts.BasicCommandsHandler;

public abstract class CloseHandler implements BasicCommandsHandler {

    private BasicCommandsHandler nextHandler;
    private OpenHandler openHandler;

    public CloseHandler(OpenHandler openHandler) {
        this.openHandler = openHandler;
    }

    @Override
    public void setNextHandler(BasicCommandsHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String handle(String input) {
        if (input.equals("close")) {
            if (openHandler.isFileLoaded()) {
                // Clear loaded file content
                openHandler.clearLoadedFileContent();
                return "File closed. Ready to open another file.";
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
package bg.tu_varna.sit.b2.f22621756.basic_commands.models;

import bg.tu_varna.sit.b2.f22621756.basic_commands.contracts.BasicCommandsHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class OpenHandler implements BasicCommandsHandler {

    private BasicCommandsHandler nextHandler;
    private String loadedFileContent;
    private String loadedFilePath;

    @Override
    public void setNextHandler(BasicCommandsHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String handle(String input) {
        if (input.startsWith("open ")) {
            String fileLocation = input.substring(5); // Extract file location and name from input
            File file = new File(fileLocation);

            if (file.exists()) {
                loadedFileContent = readFileContent(file);
                return "File loaded: " + fileLocation;
            } else {
                try {
                    if (file.createNewFile()) {
                        loadedFileContent = "";
                        return "File created: " + fileLocation;
                    } else {
                        return "File already exists: " + fileLocation;
                    }
                } catch (IOException e) {
                    return "Failed to create file: " + fileLocation + ", Error: " + e.getMessage();
                }
            }
        } else if (Objects.nonNull(nextHandler)) {
            return nextHandler.handle(input);
        } else {
            return "Invalid command: " + input;
        }
    }

    private String readFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + file.getName() + ", Error: " + e.getMessage());
        }
        return content.toString();
    }

    public String getLoadedFileContent() {
        return loadedFileContent;
    }

    @Override
    public boolean isFileLoaded() {
        return loadedFileContent != null && !loadedFileContent.isEmpty();
    }
    public void clearLoadedFileContent() {
        loadedFileContent = null;
    }
    public String getLoadedFilePath() {
        return loadedFilePath;
    }
}

package bg.tu_varna.sit.b2.f22621756.basic_commands.contracts;

public interface BasicCommandsHandler {
    void setNextHandler(BasicCommandsHandler basicCommandsHandler);

    String handle(String command);

    boolean isFileLoaded();


}
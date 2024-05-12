package bg.tu_varna.sit.b2.f22621756.XMLfile;

public interface CommandHandler {
    void handleCommand(String command);
    void setSuccessor(CommandHandler successor);
}

package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class InvalidInteractionOptionException extends ClientCommandException {
    public InvalidInteractionOptionException() {
        super();
    }
    public InvalidInteractionOptionException(String message) {
        super(message);
    }
}

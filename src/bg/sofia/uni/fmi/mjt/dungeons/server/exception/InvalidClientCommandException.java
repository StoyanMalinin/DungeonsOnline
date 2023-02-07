package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class InvalidClientCommandException extends ServerLogicException {
    public InvalidClientCommandException() {
        super();
    }

    public InvalidClientCommandException(String message) {
        super(message);
    }
}

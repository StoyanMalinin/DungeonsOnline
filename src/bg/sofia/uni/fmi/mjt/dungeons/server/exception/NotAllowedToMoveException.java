package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class NotAllowedToMoveException extends ClientCommandException {
    public NotAllowedToMoveException() {
        super();
    }

    public NotAllowedToMoveException(String message) {
        super(message);
    }
}

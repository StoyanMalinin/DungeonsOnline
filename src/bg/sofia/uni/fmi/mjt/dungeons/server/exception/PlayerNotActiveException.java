package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class PlayerNotActiveException extends ClientCommandException {
    public PlayerNotActiveException() {
        super();
    }
    public PlayerNotActiveException(String message) {
        super(message);
    }
}

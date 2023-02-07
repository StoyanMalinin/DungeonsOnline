package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class NoAvailableMapPositionsException extends ClientCommandException {
    public NoAvailableMapPositionsException() {
        super();
    }

    public NoAvailableMapPositionsException(String message) {
        super(message);
    }
}

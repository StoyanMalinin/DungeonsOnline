package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class NoAvailablePlayersException extends ClientCommandException {

    public NoAvailablePlayersException() {
        super();
    }
    public NoAvailablePlayersException(String message) {
        super(message);
    }
}

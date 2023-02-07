package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class ClientCommandException extends ServerLogicException {
    public ClientCommandException() {
        super();
    }

    public ClientCommandException(String message) {
        super(message);
    }
}

package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class ItemDoesNotBelongToPlayerException extends ClientCommandException {
    public ItemDoesNotBelongToPlayerException() {
        super();
    }
    public ItemDoesNotBelongToPlayerException(String message) {
        super(message);
    }
}

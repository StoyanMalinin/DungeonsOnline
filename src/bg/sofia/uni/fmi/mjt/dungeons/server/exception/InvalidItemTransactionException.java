package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class InvalidItemTransactionException extends ClientCommandException {
    public InvalidItemTransactionException() {
        super();
    }
    public InvalidItemTransactionException(String message) {
        super(message);
    }
}

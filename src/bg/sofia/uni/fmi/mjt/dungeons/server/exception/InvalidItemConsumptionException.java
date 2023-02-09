package bg.sofia.uni.fmi.mjt.dungeons.server.exception;

public class InvalidItemConsumptionException extends ClientCommandException {
    public InvalidItemConsumptionException() {
        super();
    }
    public InvalidItemConsumptionException(String message) {
        super(message);
    }
}

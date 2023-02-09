package bg.sofia.uni.fmi.mjt.dungeons.common;

public interface Logger {
    void logMessage(String message);
    void logException(Exception e);
    void logException(String customMessage, Exception e);
}

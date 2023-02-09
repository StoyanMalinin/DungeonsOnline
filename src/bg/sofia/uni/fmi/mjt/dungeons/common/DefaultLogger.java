package bg.sofia.uni.fmi.mjt.dungeons.common;

import java.io.*;
import java.nio.file.Path;

public class DefaultLogger {

    private static PrintStream logFile = null;

    public static synchronized void logMessageToConsole(String message) {
        if (message == null) {
            logMessageToConsole("[Logger-Error] The message cannot be null");
            return;
        }

        System.out.println(message);
    }

    public static synchronized void logMessage(String message) {
        logMessageToConsole(message);
        if (logFile != null) {

            logFile.println(message);
            if (logFile.checkError() == true) {
                logMessageToConsole("[Logger-Error] there was a problem writing to the file");
            }
        }
    }

    public static synchronized void logException(Exception exception) {
        if (exception == null) {
            logMessageToConsole("[Logger-Error] The exception cannot be null");
            return;
        }

        logMessageToConsole(exception.getMessage());
        if (logFile != null) {

            logFile.println(exception.getMessage());
            exception.printStackTrace(logFile);
            if (logFile.checkError() == true) {
                logMessageToConsole("[Logger-Error] there was a problem writing to the file");
            }
        }
    }

    public static synchronized void logException(String customMessage, Exception e) {
        logMessageToConsole(customMessage);

        if (logFile != null) {
            logFile.println(customMessage);
            logFile.println(e.getMessage());
            e.printStackTrace(logFile);

            if (logFile.checkError() == true) {
                logMessageToConsole("[Logger-Error] there was a problem writing to the file");
            }
        }
    }

    public static synchronized void changeLogFile(Path newLogFilePath) {
        if (logFile != null) {

            logFile.close();
            if (logFile.checkError() == true) {
                logMessageToConsole("[Logger-Error] There was a problem closing the file");
            }
        }

        if (newLogFilePath != null) {
            try {
                logFile = new PrintStream(newLogFilePath.toFile());
            } catch (FileNotFoundException e) {
                logMessageToConsole("[Logger-Error] The provided file was not found");
            }

            if (logFile.checkError() == true) {
                logMessageToConsole("[Logger-Error] There was a problem opening the file");
            }
        }
    }

    public static synchronized void closeLogFile() {
        changeLogFile(null);
    }
}

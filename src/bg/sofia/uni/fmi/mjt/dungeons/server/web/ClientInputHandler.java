package bg.sofia.uni.fmi.mjt.dungeons.server.web;

import bg.sofia.uni.fmi.mjt.dungeons.common.DefaultLogger;
import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.common.command.CommandToServer;
import bg.sofia.uni.fmi.mjt.dungeons.common.command.UnregisterPlayerCommand;
import bg.sofia.uni.fmi.mjt.dungeons.server.CommandExecutor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientInputHandler implements Runnable {

    public CommandExecutor executor;
    public Socket clientSocket;
    private PlayerId playerId;

    public ClientInputHandler(PlayerId playerId, CommandExecutor executor, Socket clientSocket) {
        if (executor == null) {
            throw new IllegalArgumentException("Executor cannot be null");
        }
        if (clientSocket == null) {
            throw new IllegalArgumentException("ClientSocket cannot be null");
        }
        if (playerId == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        this.executor = executor;
        this.clientSocket = clientSocket;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        try (ObjectInputStream stream = new ObjectInputStream(clientSocket.getInputStream())) {
            while (true) {
                CommandToServer command = (CommandToServer) stream.readObject();
                executor.executeCommand(command);
            }
        } catch (IOException e) {
            DefaultLogger.logMessage("Disconnecting client with address: " + clientSocket.getInetAddress());
            executor.executeCommand(new UnregisterPlayerCommand(playerId));
        } catch (ClassNotFoundException e) {
            DefaultLogger.logMessage("The client has sent an invalid request");
        }
    }
}

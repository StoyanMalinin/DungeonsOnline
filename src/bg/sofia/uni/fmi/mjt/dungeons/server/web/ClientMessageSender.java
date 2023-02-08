package bg.sofia.uni.fmi.mjt.dungeons.server.web;

import bg.sofia.uni.fmi.mjt.dungeons.common.ClientPlayerView;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.PlayerStateMonitor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMessageSender implements Runnable {

    private Socket clientSocket;
    private Player player;

    public ClientMessageSender(Socket clientSocket, Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (clientSocket == null) {
            throw new IllegalArgumentException("ClientSocket cannot be null");
        }

        this.clientSocket = clientSocket;
        this.player = player;
    }

    @Override
    public void run() {
        try (ObjectOutputStream stream = new ObjectOutputStream(clientSocket.getOutputStream())) {
            stream.writeObject(player.getId());

            while (true) {
                synchronized (player.getStateMonitor()) {
                    stream.writeObject(new ClientPlayerView(player.getState()));
                    player.getStateMonitor().wait();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not connect to player. The server will remove it from the game.");
        } catch (InterruptedException e) {
            System.out.println("Server thread was interrupted");
        }
    }
}

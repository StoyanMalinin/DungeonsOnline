package bg.sofia.uni.fmi.mjt.dungeons.client;

import bg.sofia.uni.fmi.mjt.dungeons.common.ClientPlayerView;
import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 4444;

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", SERVER_PORT);
             ObjectOutputStream serverOutput = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream serverInput = new ObjectInputStream(socket.getInputStream());
             Scanner userInput = new Scanner(System.in)) {

            PlayerId id = (PlayerId) serverInput.readObject();
            DefaultLogger.logMessage("Connected as player with id: " + id.toChar());

            UserInputHandler uih = new UserInputHandler(id, userInput, serverOutput);
            new Thread(uih).start();

            while (true) {
                ClientPlayerView view = (ClientPlayerView) serverInput.readObject();
                System.out.println(view.toString());
            }

        } catch (IOException e) {
            DefaultLogger.logMessage("There is a problem with the network communication");
        } catch (ClassNotFoundException e) {
            DefaultLogger.logMessage("There is a problem with the server communication protocol");
        }

    }
}

package bg.sofia.uni.fmi.mjt.dungeons.server;

import bg.sofia.uni.fmi.mjt.dungeons.server.entity.player.Player;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailableMapPositionsException;
import bg.sofia.uni.fmi.mjt.dungeons.server.exception.NoAvailablePlayersException;
import bg.sofia.uni.fmi.mjt.dungeons.server.web.ClientInputHandler;
import bg.sofia.uni.fmi.mjt.dungeons.server.web.ClientMessageSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int SERVER_PORT = 4444;
    private static final int MAX_EXECUTOR_THREADS = 20;

    private static final int MAP_SIZE = 7;
    private static final int MONSTER_COUNT = 3;

    public static void main(String[] args) {

        ExecutorService clientHandlers = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);
        CommandExecutor commandExecutor = new CommandExecutor(new GameMaster(MAP_SIZE, MONSTER_COUNT));

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started and listening for connect requests");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection request from client " + clientSocket.getInetAddress());

                registerClient(clientSocket, commandExecutor, clientHandlers);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }

    private static void registerClient(Socket clientSocket, CommandExecutor commandExecutor,
                                       ExecutorService clientHandlers) {
        try {
            Player player = commandExecutor.registerPlayer();

            ClientInputHandler clientInputHandler = new ClientInputHandler(player.getId(), commandExecutor, clientSocket);
            ClientMessageSender clientMessageSender = new ClientMessageSender(clientSocket, player);

            clientHandlers.execute(clientInputHandler);
            clientHandlers.execute(clientMessageSender);

            commandExecutor.refreshGameMaster();
        } catch (NoAvailablePlayersException e) {

        } catch (NoAvailableMapPositionsException e) {

        }
    }
}

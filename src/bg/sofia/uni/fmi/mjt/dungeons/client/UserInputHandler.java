package bg.sofia.uni.fmi.mjt.dungeons.client;

import bg.sofia.uni.fmi.mjt.dungeons.common.PlayerId;
import bg.sofia.uni.fmi.mjt.dungeons.common.command.MoveCommand;
import bg.sofia.uni.fmi.mjt.dungeons.common.command.PerformInteractionFromChoiceCommand;
import bg.sofia.uni.fmi.mjt.dungeons.server.map.Direction;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class UserInputHandler implements Runnable {

    private PlayerId id;
    private Scanner userInput;
    private ObjectOutputStream serverOutput;

    public UserInputHandler(PlayerId id, Scanner userInput, ObjectOutputStream serverOutput) {
        this.id = id;
        this.userInput = userInput;
        this.serverOutput = serverOutput;
    }

    @Override
    public void run() {
        try  {
            while (true) {
                String input = userInput.nextLine().strip();

                if (input.equals("l") == true) {
                    serverOutput.writeObject(new MoveCommand(id, Direction.LEFT));
                }
                else if (input.equals("r") == true) {
                    serverOutput.writeObject(new MoveCommand(id, Direction.RIGHT));
                }
                else if (input.equals("u") == true) {
                    serverOutput.writeObject(new MoveCommand(id, Direction.UP));
                }
                else if (input.equals("d") == true) {
                    serverOutput.writeObject(new MoveCommand(id, Direction.DOWN));
                } else {
                    try {
                        int choiceInd = Integer.valueOf(input);
                        serverOutput.writeObject(new PerformInteractionFromChoiceCommand(id, choiceInd));
                        serverOutput.flush();
                    } catch (NumberFormatException e) {

                        DefaultLogger.logMessage("Invalid command");
                    }
                }
            }
        } catch (IOException e) {
            DefaultLogger.logMessage("There is a problem with the network communication");
        }
    }
}

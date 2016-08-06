package Utilities;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by aseber on 5/18/16.
 */
public class CommandManager<T> {

    private LinkedBlockingQueue<Command<T>> commandList = new LinkedBlockingQueue<>();

    public void executeAllCommands(T object) {

        while (!commandList.isEmpty()) {

            executeCommand(object);

        }

    }

    public void executeCommand(T object) {

        // If the list is empty, don't execute!
        if (commandList.isEmpty()) {

            return;

        }

        Command command = commandList.poll();
        command.execute(object);

    }

}

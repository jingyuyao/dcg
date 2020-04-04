package com.dcg.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private final List<Command> commands = new ArrayList<>();

    public void add(Command command) {
        commands.add(command);
    }

    public Command getLast() {
        return commands.get(commands.size() - 1);
    }
}

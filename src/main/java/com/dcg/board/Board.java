package com.dcg.board;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandHistory;

public class Board {
    private final CommandHistory commandHistory = new CommandHistory();
    private final WorldConfiguration configuration = new WorldConfigurationBuilder().with(new CurrentPlayerSystem()).build();
    private final World world = new World(configuration.register(commandHistory));

    public Board(String[] playerNames) {
        for (String name : playerNames) {
            int entity = world.create();
            Player player = world.edit(entity).create(Player.class);
            player.name = name;
        }
    }

    public void process(Command command) {
        commandHistory.add(command);
        world.process();
    }
}

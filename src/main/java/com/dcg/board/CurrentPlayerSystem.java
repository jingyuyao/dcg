package com.dcg.board;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.One;
import com.artemis.annotations.Wire;
import com.dcg.command.CommandHistory;
import com.dcg.command.CommandKind;

@One(Player.class)
public class CurrentPlayerSystem extends BaseEntitySystem {
    ComponentMapper<Player> mPlayer;
    @Wire
    CommandHistory commandHistory;
    private int currentPlayerIndex = 0;

    protected void processSystem() {
        if (commandHistory.getLast().kind == CommandKind.ADVANCE) {
            currentPlayerIndex = (currentPlayerIndex + 1) % getEntityIds().size();
            System.out.println("currentPlayerIndex: " + currentPlayerIndex);
        }
    }

    public Player getCurrentPlayer() {
        return mPlayer.get(getEntityIds().get(currentPlayerIndex));
    }
}

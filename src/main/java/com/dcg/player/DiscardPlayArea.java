package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.card.DiscardPile;
import com.dcg.card.MoveLocation;
import com.dcg.card.PlayArea;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class DiscardPlayArea extends Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  PlayerOwnedSystem playerOwnedSystem;
  ComponentMapper<Player> mPlayer;

  public DiscardPlayArea(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    Aspect.Builder playArea = Aspect.all(Card.class, PlayerOwned.class, PlayArea.class);
    for (int cardEntity : playerOwnedSystem.filter(playArea, playerEntity)) {
      commandChain.addStart(new MoveLocation(cardEntity, DiscardPile.class));
    }
  }

  @Override
  public String toString() {
    return super.toString() + mPlayer.get(playerEntity);
  }
}

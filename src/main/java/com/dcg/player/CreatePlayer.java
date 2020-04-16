package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.battle.CreateUnit;
import com.dcg.card.CreateCard;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.turn.AdjustPower;

public class CreatePlayer extends Command {
  private final String name;
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    for (int i = 0; i < 6; i++) {
      commandChain.addEnd(
          new CreateCard("Diplomacy", 0).addEffects(new AdjustPower(1)).setOwner(playerEntity));
    }
    commandChain.addEnd(
        new CreateCard("Pot of Greed", 0).addEffects(new DrawCards(2)).setOwner(playerEntity),
        new CreateCard("Eager Owlet", 0)
            .addEffects(new CreateUnit("Eager Owlet", 2))
            .setOwner(playerEntity),
        new CreateCard("Secret Pages", 0).addEffects(new AdjustPower(2)).setOwner(playerEntity));
    commandChain.addEnd(new DrawCards(playerEntity, 5));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

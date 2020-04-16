package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.battle.AddDefense;
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
  protected void run() {
    // TODO: inherit from CreateEntity then add AdvanceTurn
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    commandChain.addEnd(
        new CreateCard("Diplomacy", 0)
            .addOnCreateEffects(new AdjustPower(1))
            .setOwner(playerEntity),
        new CreateCard("Diplomacy", 0)
            .addOnCreateEffects(new AdjustPower(1))
            .setOwner(playerEntity),
        new CreateCard("Worn Shield", 0)
            .addOnCreateEffects(new AddDefense(2))
            .setOwner(playerEntity),
        new CreateCard("Eager Owlet", 0)
            .addOnCreateEffects(new CreateUnit("Eager Owlet", 2))
            .setOwner(playerEntity),
        new CreateCard("Withering Witch", 0)
            .addOnCreateEffects(
                new CreateUnit("Withering Witch", 2)
                    .addOnCreateEffects(new AddDefense(3).toOwner()))
            .setOwner(playerEntity),
        new CreateCard("Secret Pages", 0)
            .addOnCreateEffects(new AdjustPower(2))
            .setOwner(playerEntity));
    commandChain.addEnd(new DrawCards(playerEntity, 5));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

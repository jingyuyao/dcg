package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.battle.AddDefense;
import com.dcg.battle.CreateUnit;
import com.dcg.card.CreateCard;
import com.dcg.command.CommandChain;
import com.dcg.game.CreateEntity;
import com.dcg.turn.AdjustPower;

public class CreatePlayer extends CreateEntity {
  private final String name;
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    this.name = name;
    addOnEnterEffects(new FillHand(5), new AdvanceTurn());
  }

  @Override
  protected void run() {
    int playerEntity = createEntity();
    mPlayer.create(playerEntity).name = name;
    commandChain.addEnd(
        new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)).setOwner(playerEntity),
        new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)).setOwner(playerEntity),
        new CreateCard("Worn Shield", 0)
            .addOnEnterEffects(new AddDefense(2))
            .setOwner(playerEntity),
        new CreateCard("Eager Owlet", 0)
            .addOnEnterEffects(new CreateUnit("Eager Owlet", 2))
            .setOwner(playerEntity),
        new CreateCard("Withering Witch", 0)
            .addOnEnterEffects(
                new CreateUnit("Withering Witch", 2).addOnEnterEffects(new AddDefense(3).toOwner()))
            .setOwner(playerEntity),
        new CreateCard("Secret Pages", 0)
            .addOnEnterEffects(new AdjustPower(2))
            .setOwner(playerEntity));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

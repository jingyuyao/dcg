package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.battle.AdjustDefense;
import com.dcg.battle.AdjustStrength;
import com.dcg.battle.CreateUnit;
import com.dcg.battle.DestroyUnit;
import com.dcg.battle.PerformBattle;
import com.dcg.battle.SetFlying;
import com.dcg.battle.SetLifeSteal;
import com.dcg.card.CreateCard;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreatePlayer extends CreateEntity {
  private final String name;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    this.name = name;
    addOnEnterEffects(new AdvanceTurn());
    addOnLeaveEffects(new DiscardPlayArea(), new DrawCards(5), new PerformBattle());
  }

  @Override
  protected void run() {
    int playerEntity = createEntity();
    mPlayer.create(playerEntity).name = name;
    commandChain.addEnd(
        generateCards().stream()
            .map(commandBuilder -> commandBuilder.build(world, playerEntity))
            .collect(Collectors.toList()));
    commandChain.addEnd(new DrawCards(5).build(world, playerEntity));
  }

  @SuppressWarnings("SpellCheckingInspection")
  private List<CommandBuilder> generateCards() {
    return Arrays.asList(
        new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
        new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
        new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
        new CreateCard("Worn Shield", 0).addOnEnterEffects(new AdjustDefense(2)),
        new CreateCard("Deathstrike", 3).addOnEnterEffects(new AdjustPower(1), new DestroyUnit()),
        new CreateCard("Eager Owlet", 0)
            .addOnEnterEffects(
                new CreateUnit("Eager Owlet", 2).addOnEnterEffects(new SetFlying(true))),
        new CreateCard("Stonepowder Alchemist", 0)
            .addOnEnterEffects(
                new CreateUnit("Stonepowder Alchemist", 2)
                    .addOnEnterEffects(new SetLifeSteal(true))),
        new CreateCard("Wisdom of the Elders", 5).addOnEnterEffects(new DrawCards(2)),
        new CreateCard("Oni Ronin", 1)
            .addOnEnterEffects(
                new CreateUnit("Oni Ronin", 1), new AdjustStrength(1), new AdjustStrength(1)),
        new CreateCard("Xenan Cupbearer", 0)
            .addOnEnterEffects(
                new CreateUnit("Xenan Cupbearer", 1).addOnEnterEffects(new AdjustDefense(1)),
                new AdjustHp(1)),
        new CreateCard("Grenadin Drone", 0)
            .addOnEnterEffects(new CreateUnit("Grenadin Drone", 2), new CreateUnit("Grenadin", 1)),
        new CreateCard("Withering Witch", 0)
            .addOnEnterEffects(
                new CreateUnit("Withering Witch", 2).addOnEnterEffects(new AdjustDefense(3))),
        new CreateCard("Secret Pages", 0).addOnEnterEffects(new AdjustPower(2)));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

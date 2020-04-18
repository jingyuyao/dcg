package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.battle.PerformBattle;
import com.dcg.card.Cards;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import java.util.List;
import java.util.Random;

public class CreatePlayer extends CreateEntity {
  private final String name;
  @Wire protected Random random;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    this.name = name;
    addOnEnterEffects(new AdvanceTurn());
    addOnLeaveEffects(new DiscardPlayArea(), new DrawCards(5), new PerformBattle());
  }

  @Override
  protected void run(List<Integer> input) {
    int playerEntity = createEntity();
    mPlayer.create(playerEntity).name = name;
    for (CommandBuilder builder : Cards.BASIC_CARDS) {
      commandChain.addEnd(builder.build(world, playerEntity));
    }
    commandChain.addEnd(
        Cards.BASIC_UNITS.get(random.nextInt(Cards.BASIC_UNITS.size())).build(world, playerEntity));
    commandChain.addEnd(new DrawCards(5).build(world, playerEntity));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

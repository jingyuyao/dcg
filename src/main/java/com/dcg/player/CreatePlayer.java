package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.battle.PerformBattle;
import com.dcg.card.Cards;
import com.dcg.command.CommandBuilder;
import com.dcg.command.Target;
import com.dcg.game.CreateEntity;
import java.util.Random;

public class CreatePlayer extends CreateEntity {
  @Wire protected Random random;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    super(name);
    addOnEnterEffects(new AdvanceTurn());
    addOnLeaveEffects(new DiscardPlayArea(), new DrawCards(5), new PerformBattle());
  }

  @Override
  protected void run(Target target) {
    int playerEntity = createEntity(target);
    mPlayer.create(playerEntity);
    for (CommandBuilder builder : Cards.BASIC_CARDS) {
      commandChain.addEnd(builder.build(world, playerEntity));
    }
    commandChain.addEnd(
        Cards.BASIC_UNITS.get(random.nextInt(Cards.BASIC_UNITS.size())).build(world, playerEntity));
    commandChain.addEnd(new DrawCards(5).build(world, playerEntity));
  }
}

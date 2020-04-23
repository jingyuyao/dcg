package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.battle.PerformBattle;
import com.dcg.card.Cards;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import java.util.List;
import java.util.Random;

public class CreatePlayer extends CreateEntity {
  @Wire protected Random random;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    super(name);
    // TODO: we need to discard first before playing cards to avoid current cards counting previous
    // cards for its effects, possibly moving on leave effects into advance turn?
    addOnEnterEffects(new CreateAction(new AdvanceTurn()));
    addOnLeaveEffects(new DiscardPlayArea(), new DrawCards(5), new PerformBattle());
  }

  @Override
  protected void run(int originEntity, List<Integer> targets) {
    int playerEntity = createEntity(originEntity);
    mPlayer.create(playerEntity);
    for (CommandBuilder builder : Cards.createBasicCards()) {
      commandChain.addEnd(builder.build(world, playerEntity));
    }
    List<CommandBuilder> basicUnits = Cards.createBasicUnits();
    commandChain.addEnd(
        basicUnits.get(random.nextInt(basicUnits.size())).build(world, playerEntity));
    commandChain.addEnd(new DrawCards(5).build(world, playerEntity));
  }
}

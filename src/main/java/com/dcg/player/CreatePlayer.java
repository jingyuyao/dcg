package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Cards;
import com.dcg.command.CommandArgs;
import com.dcg.game.CreateEntity;
import com.dcg.location.PlayerDeck;
import com.dcg.turn.AdvanceTurn;
import java.util.List;
import java.util.Random;

public class CreatePlayer extends CreateEntity {
  @Wire protected Random random;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    super(name);
    addOnEnterEffects(new CreateAction(new AdvanceTurn()));
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    int playerEntity = createEntity(originEntity);
    mPlayer.create(playerEntity);
    for (CreateEntity createEntity : Cards.createBasicCards()) {
      commandChain.addEnd(createEntity.tags(PlayerDeck.class).build(world, playerEntity));
    }
    List<CreateEntity> basicUnits = Cards.createBasicUnits();
    CreateEntity createEntity = basicUnits.get(random.nextInt(basicUnits.size()));
    commandChain.addEnd(createEntity.tags(PlayerDeck.class).build(world, playerEntity));
    commandChain.addEnd(new DrawCards(5).build(world, playerEntity));
  }
}

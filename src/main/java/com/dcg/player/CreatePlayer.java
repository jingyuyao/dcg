package com.dcg.player;

import static com.dcg.action.CreateAction.action;
import static com.dcg.player.DrawCards.autoDraw;

import com.artemis.ComponentMapper;
import com.dcg.card.Cards;
import com.dcg.command.CommandData;
import com.dcg.game.CreateEntity;
import com.dcg.location.PlayerDeck;
import com.dcg.turn.AdvanceTurn;

public class CreatePlayer extends CreateEntity {
  private final CreateEntity basicUnit;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name, CreateEntity basicUnit) {
    super(name);
    this.basicUnit = basicUnit;
    addOnEnterEffects(action(new AdvanceTurn()));
  }

  @Override
  protected void run(CommandData data) {
    int playerEntity = createEntity();
    mPlayer.create(playerEntity);
    for (CreateEntity createEntity : Cards.createBasicCards()) {
      commandChain.addEnd(createEntity.tags(PlayerDeck.class).build(world, playerEntity));
    }
    commandChain.addEnd(basicUnit.tags(PlayerDeck.class).build(world, playerEntity));
    commandChain.addEnd(autoDraw(5).build(world, playerEntity));
  }
}

package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.action.CreateAction;
import com.dcg.card.Cards;
import com.dcg.command.CommandArgs;
import com.dcg.game.CreateEntity;
import com.dcg.location.PlayerDeck;
import com.dcg.turn.AdvanceTurn;
import java.util.List;

public class CreatePlayer extends CreateEntity {
  private final CreateEntity basicUnit;
  protected ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name, CreateEntity basicUnit) {
    super(name);
    this.basicUnit = basicUnit;
    addOnEnterEffects(new CreateAction(new AdvanceTurn()));
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    int playerEntity = createEntity(originEntity);
    Player player = mPlayer.create(playerEntity);
    player.hp = 25;
    for (CreateEntity createEntity : Cards.createBasicCards()) {
      commandChain.addEnd(createEntity.tags(PlayerDeck.class).build(world, playerEntity));
    }
    commandChain.addEnd(basicUnit.tags(PlayerDeck.class).build(world, playerEntity));
    commandChain.addEnd(new DrawCards(5).build(world, playerEntity));
  }
}

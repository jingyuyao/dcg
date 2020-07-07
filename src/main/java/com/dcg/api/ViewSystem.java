package com.dcg.api;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.action.Action;
import com.dcg.api.CardView.CardKind;
import com.dcg.battle.Unit;
import com.dcg.card.Basic;
import com.dcg.card.Card;
import com.dcg.card.Colors;
import com.dcg.card.HasUnit;
import com.dcg.card.Spell;
import com.dcg.command.CommandChain;
import com.dcg.game.Common;
import com.dcg.game.CoreSystem;
import com.dcg.location.DiscardPile;
import com.dcg.location.ForgeRow;
import com.dcg.location.Hand;
import com.dcg.location.MercenaryDeck;
import com.dcg.location.PlayArea;
import com.dcg.location.ThroneDeck;
import com.dcg.player.Player;
import com.dcg.turn.Turn;
import java.util.List;
import java.util.stream.Collectors;

/** Provide views into the game world. */
public class ViewSystem extends BaseSystem {
  @Wire CommandChain commandChain;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Common> mCommon;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<HasUnit> mHasUnit;
  protected ComponentMapper<Spell> mSpell;
  protected ComponentMapper<Basic> mBasic;
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Action> mAction;

  public GameView getGameView(String playerName) {
    int playerEntity =
        coreSystem.findByName(playerName, Aspect.all(Player.class)).findFirst().orElse(-1);
    Turn turn = coreSystem.getTurn();
    return new GameView(
        coreSystem.toName(coreSystem.getCurrentPlayerEntity()),
        coreSystem.toName(turn.previousPlayerEntity),
        getPlayers(),
        getForgeRow(),
        getThroneDeck(),
        getMercenaryDeck(),
        getPlayArea(),
        getHand(playerEntity),
        getDiscardPile(playerEntity),
        getAttackingUnits(),
        getDefendingUnits(),
        getRecentExecutions());
  }

  private List<PlayerView> getPlayers() {
    return coreSystem
        .getStream(Aspect.all(Player.class))
        .map(
            playerEntity -> {
              Common common = mCommon.get(playerEntity);
              Player player = mPlayer.get(playerEntity);
              Turn turn = mTurn.has(playerEntity) ? mTurn.get(playerEntity) : null;
              List<ActionView> actions = getActions(playerEntity);
              return new PlayerView(playerEntity, common, player, turn, actions);
            })
        .collect(Collectors.toList());
  }

  private List<CardView> getForgeRow() {
    return coreSystem
        .getStream(Aspect.all(Card.class, ForgeRow.class))
        .map(this::toCardView)
        .collect(Collectors.toList());
  }

  private List<CardView> getThroneDeck() {
    return coreSystem
        .getStream(Aspect.all(Card.class, ThroneDeck.class))
        .map(this::toCardView)
        .collect(Collectors.toList());
  }

  private List<CardView> getMercenaryDeck() {
    return coreSystem
        .getStream(Aspect.all(Card.class, MercenaryDeck.class))
        .map(this::toCardView)
        .collect(Collectors.toList());
  }

  private List<CardView> getPlayArea() {
    return coreSystem
        .getStream(Aspect.all(Card.class, PlayArea.class))
        .map(this::toCardView)
        .collect(Collectors.toList());
  }

  private List<CardView> getHand(int playerEntity) {
    return coreSystem
        .getChildren(playerEntity, Aspect.all(Card.class, Hand.class))
        .map(this::toCardView)
        .collect(Collectors.toList());
  }

  private List<CardView> getDiscardPile(int playerEntity) {
    return coreSystem
        .getChildren(playerEntity, Aspect.all(Card.class, DiscardPile.class))
        .map(this::toCardView)
        .collect(Collectors.toList());
  }

  private List<UnitView> getAttackingUnits() {
    return coreSystem.getAttackingEntities().map(this::toUnitView).collect(Collectors.toList());
  }

  private List<UnitView> getDefendingUnits() {
    return coreSystem.getDefendingEntities().map(this::toUnitView).collect(Collectors.toList());
  }

  private CardView toCardView(int cardEntity) {
    Common common = mCommon.get(cardEntity);
    Card card = mCard.get(cardEntity);
    CardKind kind = getCardKind(cardEntity);
    List<String> colors = getCardColors(cardEntity);
    int strength = mHasUnit.has(cardEntity) ? mHasUnit.get(cardEntity).strength : 0;
    List<ActionView> actions = getActions(cardEntity);
    return new CardView(cardEntity, common, card, kind, colors, strength, actions);
  }

  private CardKind getCardKind(int cardEntity) {
    if (mHasUnit.has(cardEntity)) {
      return CardKind.UNIT;
    } else if (mSpell.has(cardEntity)) {
      return CardKind.SPELL;
    } else if (mBasic.has(cardEntity)) {
      return CardKind.BASIC;
    } else {
      return CardKind.UNKNOWN;
    }
  }

  private List<String> getCardColors(int cardEntity) {
    return Colors.ALL.stream()
        .filter(clazz -> world.getMapper(clazz).has(cardEntity))
        .map(Class::getSimpleName)
        .collect(Collectors.toList());
  }

  private UnitView toUnitView(int unitEntity) {
    Common common = mCommon.get(unitEntity);
    Unit unit = mUnit.get(unitEntity);
    List<ActionView> actions = getActions(unitEntity);
    return new UnitView(unitEntity, common, unit, actions);
  }

  private List<ActionView> getActions(int ownerEntity) {
    return coreSystem
        .getChildren(ownerEntity, Aspect.all(Action.class))
        .map(
            actionEntity -> {
              Common common = mCommon.get(actionEntity);
              Action action = mAction.get(actionEntity);
              return new ActionView(actionEntity, common, action);
            })
        .collect(Collectors.toList());
  }

  private List<ExecutionView> getRecentExecutions() {
    return commandChain.getExecutionBuffer().stream()
        .map(
            execution ->
                new ExecutionView(
                    coreSystem.toName(execution.getExecutor()), execution.getCommand()))
        .collect(Collectors.toList());
  }

  @Override
  protected void processSystem() {}
}

package com.dcg.api;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.api.CardView.CardColor;
import com.dcg.api.CardView.CardKind;
import com.dcg.api.CardView.CardLocation;
import com.dcg.api.UnitView.UnitState;
import com.dcg.battle.Attacking;
import com.dcg.battle.Defending;
import com.dcg.battle.Unit;
import com.dcg.card.Basic;
import com.dcg.card.Black;
import com.dcg.card.Blue;
import com.dcg.card.Card;
import com.dcg.card.Colors;
import com.dcg.card.Green;
import com.dcg.card.HasUnit;
import com.dcg.card.Red;
import com.dcg.card.Spell;
import com.dcg.card.Yellow;
import com.dcg.game.Common;
import com.dcg.game.CoreSystem;
import com.dcg.location.DiscardPile;
import com.dcg.location.ForgeDeck;
import com.dcg.location.ForgeRow;
import com.dcg.location.Hand;
import com.dcg.location.MercenaryDeck;
import com.dcg.location.PlayArea;
import com.dcg.location.PlayerDeck;
import com.dcg.location.ThroneDeck;
import com.dcg.player.Player;
import com.dcg.turn.Turn;
import java.util.List;
import java.util.stream.Collectors;

/** Provide views into the game world. */
public class ViewSystem extends BaseSystem {
  protected CoreSystem coreSystem;
  protected ComponentMapper<Common> mCommon;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<HasUnit> mHasUnit;
  protected ComponentMapper<Spell> mSpell;
  protected ComponentMapper<Basic> mBasic;
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Attacking> mAttacking;
  protected ComponentMapper<Defending> mDefending;
  protected ComponentMapper<Action> mAction;
  protected ComponentMapper<ForgeDeck> mForgeDeck;
  protected ComponentMapper<ForgeRow> mForgeRow;
  protected ComponentMapper<ThroneDeck> mThroneDeck;
  protected ComponentMapper<MercenaryDeck> mMercenaryDeck;
  protected ComponentMapper<PlayerDeck> mPlayerDeck;
  protected ComponentMapper<DiscardPile> mDiscardPile;
  protected ComponentMapper<Hand> mHand;
  protected ComponentMapper<PlayArea> mPlayArea;

  public GameView getGameView(String playerName) {
    int playerEntity =
        coreSystem.findByName(playerName, Aspect.all(Player.class)).findFirst().orElse(-1);
    Turn turn = coreSystem.getTurn();
    return new GameView(
        playerEntity,
        coreSystem.getCurrentPlayerEntity(),
        turn.previousPlayerEntity,
        getPlayers(),
        getCards(playerEntity),
        getUnits());
  }

  private List<PlayerView> getPlayers() {
    return coreSystem
        .getStream(Aspect.all(Player.class))
        .map(this::toPlayerView)
        .collect(Collectors.toList());
  }

  private PlayerView toPlayerView(int playerEntity) {
    Common common = mCommon.get(playerEntity);
    Player player = mPlayer.get(playerEntity);
    Turn turn = mTurn.has(playerEntity) ? mTurn.get(playerEntity) : null;
    List<ActionView> actions = getActions(playerEntity);
    int totalDeckSize = (int) coreSystem.getChildren(playerEntity, Aspect.all(Card.class)).count();
    int currentDeckSize =
        (int)
            coreSystem.getChildren(playerEntity, Aspect.all(Card.class, PlayerDeck.class)).count();
    return new PlayerView(
        playerEntity, common, player, turn, totalDeckSize, currentDeckSize, actions);
  }

  private List<CardView> getCards(int playerEntity) {
    return coreSystem
        .getStream(Aspect.all(Card.class).exclude(ForgeDeck.class))
        .map(this::toCardView)
        .filter(card -> this.filterCardView(playerEntity, card))
        .collect(Collectors.toList());
  }

  private List<UnitView> getUnits() {
    return coreSystem
        .getStream(Aspect.all(Unit.class))
        .map(this::toUnitView)
        .collect(Collectors.toList());
  }

  private CardView toCardView(int cardEntity) {
    Common common = mCommon.get(cardEntity);
    int ownerEntity = coreSystem.getParent(cardEntity);
    Card card = mCard.get(cardEntity);
    CardKind kind = getCardKind(cardEntity);
    CardLocation location = getCardLocation(cardEntity);
    List<CardColor> colors = getCardColors(cardEntity);
    int strength = mHasUnit.has(cardEntity) ? mHasUnit.get(cardEntity).strength : 0;
    List<ActionView> actions = getActions(cardEntity);
    return new CardView(
        cardEntity, common, ownerEntity, card, kind, location, colors, strength, actions);
  }

  private boolean filterCardView(int playerEntity, CardView card) {
    return card.ownerEntity == -1
        || card.ownerEntity == playerEntity
        || card.location == CardLocation.PLAY_AREA;
  }

  private CardKind getCardKind(int cardEntity) {
    if (mHasUnit.has(cardEntity)) {
      return CardKind.UNIT;
    } else if (mSpell.has(cardEntity)) {
      return CardKind.SPELL;
    } else if (mBasic.has(cardEntity)) {
      return CardKind.BASIC;
    }
    throw new RuntimeException("Not possible");
  }

  private CardLocation getCardLocation(int cardEntity) {
    if (mForgeDeck.has(cardEntity)) {
      return CardLocation.FORGE_DECK;
    } else if (mForgeRow.has(cardEntity)) {
      return CardLocation.FORGE_ROW;
    } else if (mThroneDeck.has(cardEntity)) {
      return CardLocation.THRONE_DECK;
    } else if (mMercenaryDeck.has(cardEntity)) {
      return CardLocation.MERCENARY_DECK;
    } else if (mPlayerDeck.has(cardEntity)) {
      return CardLocation.PLAYER_DECK;
    } else if (mHand.has(cardEntity)) {
      return CardLocation.HAND;
    } else if (mDiscardPile.has(cardEntity)) {
      return CardLocation.DISCARD_PILE;
    } else if (mPlayArea.has(cardEntity)) {
      return CardLocation.PLAY_AREA;
    }
    throw new RuntimeException("Not possible");
  }

  private List<CardColor> getCardColors(int cardEntity) {
    return Colors.ALL.stream()
        .filter(clazz -> world.getMapper(clazz).has(cardEntity))
        .map(this::toColor)
        .collect(Collectors.toList());
  }

  private UnitView toUnitView(int unitEntity) {
    int ownerEntity = coreSystem.getParent(unitEntity);
    Common common = mCommon.get(unitEntity);
    Unit unit = mUnit.get(unitEntity);
    UnitState state = getUnitState(unitEntity);
    List<CardColor> colors = getCardColors(unit.cardEntity);
    List<ActionView> actions = getActions(unitEntity);
    return new UnitView(unitEntity, common, ownerEntity, unit, state, colors, actions);
  }

  private UnitState getUnitState(int unitEntity) {
    if (mAttacking.has(unitEntity)) {
      return UnitState.ATTACKING;
    } else if (mDefending.has(unitEntity)) {
      return UnitState.DEFENDING;
    }
    throw new RuntimeException("Not possible");
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

  private CardColor toColor(Class<?> clazz) {
    if (clazz.equals(Red.class)) {
      return CardColor.RED;
    }
    if (clazz.equals(Green.class)) {
      return CardColor.GREEN;
    }
    if (clazz.equals(Blue.class)) {
      return CardColor.BLUE;
    }
    if (clazz.equals(Yellow.class)) {
      return CardColor.YELLOW;
    }
    if (clazz.equals(Black.class)) {
      return CardColor.BLACK;
    }
    throw new RuntimeException("Not possible");
  }

  @Override
  protected void processSystem() {}
}

package com.dcg.card;

import com.dcg.action.CreateAction;
import com.dcg.battle.AdjustDefense;
import com.dcg.battle.AdjustStrength;
import com.dcg.battle.CreateUnit;
import com.dcg.battle.DestroyUnit;
import com.dcg.battle.SetBerserk;
import com.dcg.battle.SetEndurance;
import com.dcg.battle.SetFlying;
import com.dcg.battle.SetLifeSteal;
import com.dcg.battle.SetUnblockable;
import com.dcg.command.CommandBuilder;
import com.dcg.commandargs.TotalAttackingUnits;
import com.dcg.game.CreateEntity;
import com.dcg.player.AdjustHp;
import com.dcg.player.AdjustPower;
import com.dcg.player.DrawCards;
import com.dcg.player.RefreshWrapTokens;
import com.dcg.targetfilter.MaxStrength;
import com.dcg.targetsource.ActivePlayers;
import com.dcg.targetsource.AllUnits;
import com.dcg.targetsource.AttackingUnits;
import com.dcg.targetsource.DefendingUnits;
import com.dcg.targetsource.ForgeRowCards;
import com.dcg.targetsource.VoidbindableCards;
import com.dcg.triggercondition.AnyDefendingUnit;
import com.dcg.triggercondition.MinDefendingUnitCount;
import com.dcg.triggercondition.MinPower;
import com.dcg.triggercondition.PlayedTag;
import com.dcg.triggercondition.ThroneActive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class Cards {
  public static List<CreateEntity> createBasicCards() {
    return Arrays.asList(
        basic("Diplomacy").desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
        basic("Refresh")
            .desc("Throne: refresh your Wrap tokens")
            .addOnConditionEffects(
                action(new RefreshWrapTokens()).addTriggerConditions(new ThroneActive())),
        basic("Worn Shield")
            .desc("Throne: give a unit +2 defense")
            .addOnConditionEffects(
                action(new AdjustDefense(2).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a unit +2 defense")
                    .addTriggerConditions(new ThroneActive())),
        basic("Secret Pages").desc("Add 2 power").addOnEnterEffects(new AdjustPower(2)));
  }

  public static List<CreateEntity> createBasicUnits() {
    return Arrays.asList(
        unit("Yeti Windflyer", 4, 1)
            .tags(Red.class, Blue.class)
            .desc("Flying, deal 2 damage to a player if you are 3 or more units")
            .addOnEnterEffects(new SetFlying(true))
            .addOnConditionEffects(
                action(new AdjustHp(-2).setInputCount(1).setTargetSource(new ActivePlayers()))
                    .desc("Deal 2 damage to a player")
                    .addTriggerConditions(new MinDefendingUnitCount(3))),
        unit("Noble Firemane", 0, 2)
            .tags(Red.class, Yellow.class)
            .desc("Throne: +3 strength")
            .addOnConditionEffects(new AdjustStrength(3).addTriggerConditions(new ThroneActive())),
        unit("Eager Owlet", 0, 2)
            .tags(Green.class, Blue.class)
            .desc("Flying")
            .addOnEnterEffects(new SetFlying(true)),
        unit("Awakened Student", 0, 2)
            .tags(Yellow.class, Green.class)
            .desc("If you have a unit with 4 strength or more, this gets +2 strength")
            .addOnConditionEffects(
                new AdjustStrength(2)
                    .addTriggerConditions(new AnyDefendingUnit(unit -> unit.strength >= 4))),
        unit("Storm Lynx", 0, 1)
            .tags(Yellow.class, Blue.class)
            .desc("If you played a spell this turn, this gets +2 strength and Endurance")
            .addOnConditionEffects(
                new AdjustStrength(2).addTriggerConditions(new PlayedTag(Spell.class)),
                new SetEndurance(true).addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Grenadin Drone", 0, 2)
            .tags(Red.class, Black.class)
            .desc("Create a 1 strength Grenadin")
            .addOnEnterEffects(new CreateUnit("Grenadin", 1)),
        unit("Fearless Nomad", 0, 2)
            .tags(Red.class, Green.class)
            .desc("Berserk")
            .addOnEnterEffects(new SetBerserk(true)),
        unit("Stonepowder Alchemist", 0, 2)
            .tags(Green.class, Black.class)
            .desc("Lifesteal")
            .addOnEnterEffects(new SetLifeSteal(true)),
        unit("Xenan Cupbearer", 0, 1)
            .tags(Yellow.class, Black.class)
            .desc("1 defense, heals 1 hp")
            .addOnEnterEffects(new AdjustDefense(1), new AdjustHp(1)),
        unit("Withering Witch", 0, 2)
            .tags(Blue.class, Black.class)
            .desc("3 defense")
            .addOnEnterEffects(new AdjustDefense(3)));
  }

  public static List<CreateEntity> createThroneDeck(int numPlayers) {
    List<CreateEntity> seekPowers = new ArrayList<>();
    for (int i = 0; i < numPlayers * 2; i++) {
      seekPowers.add(
          spell("Seek Power", 3)
              .desc("Add 2 power, Throne: may banish this to create a 2 strength Cavalry")
              .addOnEnterEffects(new AdjustPower(2))
              .addOnConditionEffects(
                  action(new DeleteCard().chain(new CreateUnit("Cavalry", 2)))
                      .desc("Banish this to create a 2 strength Cavalry")
                      .addTriggerConditions(new ThroneActive())));
    }
    return seekPowers;
  }

  public static List<CreateEntity> createMercenaries() {
    List<CreateEntity> mercenaries = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      mercenaries.add(
          unit("Veteran Mercenary", 2, 2)
              .desc("Add 1 power, can become any 1 color")
              .addOnEnterEffects(new AdjustPower(1), new MercenaryEnter())
              .addOnLeaveEffects(new MercenaryExit()));
    }
    return mercenaries;
  }

  public static List<CreateEntity> createForgeDeck() {
    return Arrays.asList(
        unit("Beckoning Lumen", 3, 3)
            .tags(Yellow.class)
            .desc("Add 1 power, Yellow: Gain 2 life")
            .addOnEnterEffects(new AdjustPower(1))
            .addOnConditionEffects(
                new AdjustHp(2).addTriggerConditions(new PlayedTag(Yellow.class))),
        unit("Jotun Punter", 4, 4)
            .canWrap()
            .tags(Blue.class)
            .desc("Give a unit flying")
            .addOnEnterEffects(
                action(new SetFlying(true).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a unit flying")),
        unit("Amethyst Acolyte", 3, 2)
            .canWrap()
            .tags(Black.class)
            .desc("Give a unit -2 strength")
            .addOnEnterEffects(
                action(new AdjustStrength(-2).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a unit -2 strength")),
        unit("Tinker Apprentice", 1, 1)
            .tags(Green.class)
            .desc("Add 1 power, give a unit +1 strength")
            .addOnEnterEffects(
                new AdjustPower(1),
                action(new AdjustStrength(1).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a unit +1 strength")),
        unit("Throne Warden", 4, 2)
            .canWrap()
            .tags(Green.class)
            .desc("Endurance, gain HP equal to # of attacking units")
            .addOnEnterEffects(new SetEndurance(true), new AdjustHp(new TotalAttackingUnits())),
        unit("Stone shaker", 4, 1)
            .tags(Red.class)
            .desc("Add 2 power, Berserk, add 2 strength to generated 7 or more power")
            .addOnEnterEffects(new AdjustPower(2), new SetBerserk(true))
            .addOnConditionEffects(new AdjustStrength(2).addTriggerConditions(new MinPower(7))),
        unit("Temple Scribe", 1, 1)
            .tags(Yellow.class)
            .desc("Add 1 power, draw 1 card if you played any spell")
            .addOnEnterEffects(new AdjustPower(1))
            .addOnConditionEffects(
                new DrawCards(1).addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Mystic Ascendant", 6, 4)
            .tags(Yellow.class)
            .desc("Draw 1 card, add 2 strength to this if you generated 7 or more power")
            .addOnEnterEffects(new DrawCards(1))
            .addOnConditionEffects(new AdjustStrength(2).addTriggerConditions(new MinPower(7))),
        unit("Splimespitter Slug", 5, 3)
            .tags(Green.class, Black.class)
            .desc("Add 1 power, give all attacking units -1 strength")
            .addOnEnterEffects(
                new AdjustPower(1), new AdjustStrength(-1).setTargetSource(new AttackingUnits())),
        unit("Impending Doom", 4, 5)
            .tags(Black.class)
            .desc("Flying, minus 1 life")
            .addOnEnterEffects(new SetFlying(true), new AdjustHp(-1)),
        unit("Ridgeline Watcher", 4, 3)
            .tags(Blue.class)
            .desc("Add 2 defense, Voidbind")
            .addOnEnterEffects(
                new AdjustDefense(2),
                action(new DeleteCard().setInputCount(1).setTargetSource(new VoidbindableCards()))
                    .desc("Voidbind")),
        unit("Pokpok, Rockpacker", 3, 1)
            .tags(Red.class, Blue.class)
            .desc("Draw 1 card, give a unit -1 strength")
            .addOnEnterEffects(
                new DrawCards(1),
                action(new AdjustStrength(-1).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a unit -1 strength")),
        unit("Snowrager", 2, 1)
            .canWrap()
            .tags(Blue.class)
            .desc("Berserk, add 1 strength to this if you have 3 or more units")
            .addOnEnterEffects(new SetBerserk(true))
            .addOnConditionEffects(
                new AdjustStrength(1).addTriggerConditions(new MinDefendingUnitCount(3))),
        unit("Cabal Spymaster", 3, 1)
            .tags(Black.class)
            .desc("Unblockable, give all defending units with 3 or less strength Unblockable")
            .addOnEnterEffects(
                new SetUnblockable(true),
                action(
                        new SetUnblockable(true)
                            .setTargetSource(new DefendingUnits().addFilters(new MaxStrength(3))))
                    .desc("Give all defending units with 3 or less strength Unblockable")),
        unit("Oni Ronin", 1, 1)
            .canWrap()
            .tags(Red.class)
            .desc("Add 1 strength to two units")
            .addOnEnterEffects(
                action(new AdjustStrength(1).setInputCount(1, 2).setTargetSource(new AllUnits()))
                    .desc("Add 1 strength to two units")),
        unit("Treasury Guard", 4, 2)
            .tags(Green.class)
            .desc("Add 2 power, Green: +1 Defense, Endurance")
            .addOnEnterEffects(new AdjustPower(2))
            .addOnConditionEffects(
                new AdjustDefense(1).addTriggerConditions(new PlayedTag(Green.class)),
                new SetEndurance(true).addTriggerConditions(new PlayedTag(Green.class))),
        unit("Iceberg Warchief", 3, 3)
            .tags(Red.class, Blue.class)
            .desc("Red: create a 2 strength Yeti, Blue: create a 2 strength Yeti")
            .addOnConditionEffects(
                new CreateUnit("Yeti", 2).addTriggerConditions(new PlayedTag(Red.class)),
                new CreateUnit("Yeti", 2).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Lumbering Gruan", 3, 3)
            .tags(Blue.class)
            .desc("Berserk, Blue: Add 2 power")
            .addOnEnterEffects(new SetBerserk(true))
            .addOnConditionEffects(
                new AdjustPower(2).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Midnight Gale", 4, 3)
            .tags(Blue.class, Black.class)
            .desc("Add 1 power, Blue: Flying, Black: Lifesteal")
            .addOnEnterEffects(new AdjustPower(1))
            .addOnConditionEffects(
                new SetFlying(true).addTriggerConditions(new PlayedTag(Blue.class)),
                new SetLifeSteal(true).addTriggerConditions(new PlayedTag(Black.class))),
        unit("Cabal Cutthroat", 4, 2)
            .tags(Black.class)
            .desc("Add 2 power, Lifesteal, Throne: Banish a card in the forge row")
            .addOnEnterEffects(new AdjustPower(2), new SetLifeSteal(true))
            .addOnConditionEffects(
                action(new DeleteCard().setInputCount(1).setTargetSource(new ForgeRowCards()))
                    .addTriggerConditions(new ThroneActive())),
        spell("Read the Stars", 2)
            .tags(Green.class, Blue.class)
            .desc("Draw 1 card, Voidbind if you have a Flying unit")
            .addOnEnterEffects(new DrawCards(1))
            .addOnConditionEffects(
                action(new DeleteCard().setInputCount(1).setTargetSource(new VoidbindableCards()))
                    .desc("Voidbind")
                    .addTriggerConditions(new AnyDefendingUnit(unit -> unit.flying))),
        spell("Levitate", 2)
            .tags(Blue.class)
            .desc("Add 2 power, give a unit Flying")
            .addOnEnterEffects(
                new AdjustPower(2),
                action(new SetFlying(true).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a unit flying")),
        spell("Arcane Defense", 3)
            .tags(Green.class)
            .desc("Draw 1 card, give two units -1 strength")
            .addOnEnterEffects(
                new DrawCards(1),
                action(new AdjustStrength(-1).setInputCount(1, 2).setTargetSource(new AllUnits()))
                    .desc("Give two units -1 strength")),
        spell("Lightning Storm", 2)
            .tags(Blue.class)
            .desc("Voidbind, destroy all attacking units with 2 or less strength")
            .addOnEnterEffects(
                action(new DeleteCard().setInputCount(1).setTargetSource(new VoidbindableCards()))
                    .desc("Voidbind"),
                action(
                        new DestroyUnit()
                            .setTargetSource(new AttackingUnits().addFilters(new MaxStrength(2))))
                    .desc("Destroy all attacking units with 2 or less strength")),
        spell("Bolster", 3)
            .tags(Yellow.class)
            .desc("Add 2 power, give a unit +3 defense")
            .addOnEnterEffects(
                new AdjustPower(2),
                action(new AdjustDefense(3).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a unit +3 defense")),
        spell("Hipshot", 3)
            .tags(Red.class)
            .desc("Add 1 power, deal 3 damage to a player")
            .addOnEnterEffects(
                new AdjustPower(1),
                action(new AdjustHp(-3).setInputCount(1).setTargetSource(new ActivePlayers()))
                    .desc("Deal 3 damage to a player")),
        spell("Wisdom of the Elders", 5)
            .tags(Blue.class)
            .desc("Draw 2 cards")
            .addOnEnterEffects(new DrawCards(2)),
        spell("Pack Hunt", 4)
            .tags(Yellow.class)
            .desc(
                "Add 2 power, give all defending units +2 strength if you generated at least 7 power")
            .addOnEnterEffects(new AdjustPower(2))
            .addOnConditionEffects(
                action(new AdjustStrength(2).setTargetSource(new DefendingUnits()))
                    .desc("Give all defending units +2 strength")
                    .addTriggerConditions(new MinPower(7))),
        spell("Deathstrike", 3)
            .tags(Black.class)
            .desc("Add 1 power, destroy a unit")
            .addOnEnterEffects(
                new AdjustPower(1),
                action(new DestroyUnit().setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Destroy a unit")));
  }

  public static CreateAction action(CommandBuilder builder) {
    return new CreateAction(builder);
  }

  public static CreateCard basic(String name) {
    CreateCard createCard = new CreateCard(name, 0);
    createCard.tags(Basic.class);
    return createCard;
  }

  public static CreateCard spell(String name, int cost) {
    CreateCard createCard = new CreateCard(name, cost);
    createCard.tags(Spell.class);
    return createCard;
  }

  public static CreateCard unit(String name, int cost, int strength) {
    CreateCard createCard = new CreateCard(name, cost);
    createCard.hasUnit(strength);
    return createCard;
  }
}

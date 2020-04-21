package com.dcg.card;

import static com.dcg.card.Cards.UnitBuilder.unit;

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
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.condition.AnyDefendingUnit;
import com.dcg.condition.AnySpell;
import com.dcg.condition.MinAnyDefendingStrength;
import com.dcg.condition.MinPower;
import com.dcg.condition.MinUnitCount;
import com.dcg.condition.PlayAreaOrDiscardPile;
import com.dcg.effect.TotalAttackingUnits;
import com.dcg.game.CreateEntity;
import com.dcg.player.AdjustHp;
import com.dcg.player.AdjustPower;
import com.dcg.player.DrawCards;
import com.dcg.target.AttackingMaxStrength;
import com.dcg.target.AttackingUnits;
import com.dcg.target.DefendingMaxStrength;
import com.dcg.target.DefendingUnits;
import com.dcg.target.Inputs;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cards {
  public static List<CommandBuilder> BASIC_CARDS =
      Arrays.asList(
          new CreateCard("Diplomacy", 0).desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).desc("Add 1 power").addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Secret Pages", 0)
              .desc("Add 2 power")
              .addOnEnterEffects(new AdjustPower(2)));

  @SuppressWarnings("SpellCheckingInspection")
  public static List<CommandBuilder> BASIC_UNITS =
      Arrays.asList(
          unit("Eager Owlet", 0, 2).desc("Flying").addOnEnterEffects(new SetFlying(true)).build(),
          unit("Awakened Student", 0, 2)
              .desc("If you have a unit with 4 atk or more, this gets +2 atk")
              .addOnConditionEffects(
                  new AdjustStrength(2).addWorldConditions(new MinAnyDefendingStrength(4)))
              .build(),
          unit("Storm Lynx", 0, 1)
              .desc("If you played a spell this turn, this gets +2 atk and Endurance")
              .addOnConditionEffects(
                  new AdjustStrength(2).addWorldConditions(new AnySpell()),
                  new SetEndurance(true).addWorldConditions(new AnySpell()))
              .build(),
          unit("Grenadin Drone", 0, 2)
              .desc("Create a 1 atk Grenadin")
              .addOnEnterEffects(new CreateUnit("Grenadin", 1))
              .build(),
          unit("Fearless Nomad", 0, 2).addOnEnterEffects(new SetBerserk(true)).build(),
          unit("Stonepowder Alchemist", 0, 2).addOnEnterEffects(new SetLifeSteal(true)).build(),
          unit("Xenan Cupbearer", 0, 1)
              .addOnEnterEffects(new AdjustDefense(1), new AdjustHp(1))
              .build(),
          unit("Withering Witch", 0, 2).addOnEnterEffects(new AdjustDefense(3)).build());

  @SuppressWarnings("SpellCheckingInspection")
  public static List<CommandBuilder> UNIT_CARDS =
      Stream.of(
              unit("Jotun Punter", 4, 4)
                  .desc("Give a unit flying")
                  .addOnEnterEffects(new SetFlying(true).setTargetFunction(new Inputs())),
              unit("Amethyst Acolyte", 3, 2)
                  .desc("Give a unit -2 atk")
                  .addOnEnterEffects(new AdjustStrength(-2).setTargetFunction(new Inputs())),
              unit("Tinker Apprentice", 1, 1)
                  .desc("Add 1 power, give a unit +1 atk")
                  .addOnEnterEffects(
                      new AdjustPower(1), new AdjustStrength(1).setTargetFunction(new Inputs())),
              unit("Throne Warden", 4, 2)
                  .desc("Endurance, gain HP equal to # of attacking units")
                  .addOnEnterEffects(
                      new SetEndurance(true), new AdjustHp(new TotalAttackingUnits())),
              unit("Yeti Windflyer", 4, 1)
                  .desc("Flying, deal 2 damage to a player if you are 3 or more units")
                  .addOnEnterEffects(new SetFlying(true))
                  .addOnConditionEffects(
                      new AdjustHp(-2)
                          .setTargetFunction(new Inputs())
                          .addWorldConditions(new MinUnitCount(3))),
              unit("Stone shaker", 4, 1)
                  .desc("Add 2 power, Berserk, add 2 atk to generated 7 or more power")
                  .addOnEnterEffects(new AdjustPower(2), new SetBerserk(true))
                  .addOnConditionEffects(new AdjustStrength(2).addWorldConditions(new MinPower(7))),
              unit("Temple Scribe", 1, 1)
                  .desc("Add 1 power, draw 1 card if you played any spell")
                  .addOnEnterEffects(new AdjustPower(1))
                  .addOnConditionEffects(new DrawCards(1).addWorldConditions(new AnySpell())),
              unit("Mystic Ascendant", 6, 4)
                  .desc("Draw 1 card, add 2 atk to this if you generated 7 or more power")
                  .addOnEnterEffects(new DrawCards(1))
                  .addOnConditionEffects(new AdjustStrength(2).addWorldConditions(new MinPower(7))),
              unit("Splimespitter Slug", 5, 3)
                  .desc("Add 1 power, give all attacking units -1 atk")
                  .addOnEnterEffects(
                      new AdjustPower(1),
                      new AdjustStrength(-1).setTargetFunction(new AttackingUnits())),
              unit("Impending Doom", 4, 5)
                  .desc("Flying, minus 1 hp")
                  .addOnEnterEffects(new SetFlying(true), new AdjustHp(-1)),
              unit("Ridgeline Watcher", 4, 3)
                  .desc("Add 2 defense, Voidbind")
                  .addOnEnterEffects(new AdjustDefense(2), voidBind()),
              unit("Pokpok, Rockpacker", 3, 1)
                  .desc("Draw 1 card, give a unit -1 atk")
                  .addOnEnterEffects(
                      new DrawCards(1), new AdjustHp(-1).setTargetFunction(new Inputs())),
              unit("Snowrager", 2, 1)
                  .desc("Berserk, add 1 atk to this if you have 3 or more units")
                  .addOnEnterEffects(new SetBerserk(true))
                  .addOnConditionEffects(
                      new AdjustStrength(1).addWorldConditions(new MinUnitCount(3))),
              unit("Cabal Spymaster", 3, 1)
                  .desc("Unblockable, give all defending units with 3 or less atk Unblockable")
                  .addOnEnterEffects(
                      new SetUnblockable(true),
                      new CreateAction(
                          new SetUnblockable(true).setTargetFunction(new DefendingMaxStrength(3)))),
              unit("Oni Ronin", 1, 1)
                  .desc("Add 1 atk to two units")
                  .addOnEnterEffects(
                      new AdjustStrength(1).setTargetFunction(new Inputs()),
                      new AdjustStrength(1).setTargetFunction(new Inputs())))
          .map(UnitBuilder::build)
          .collect(Collectors.toList());

  @SuppressWarnings("SpellCheckingInspection")
  public static List<CommandBuilder> SPELL_CARDS =
      Arrays.asList(
          spell("Read the Stars", 2)
              .desc("Draw 1 card, Voidbind if you have a Flying unit")
              .addOnEnterEffects(new DrawCards(1))
              .addOnConditionEffects(
                  voidBind().addWorldConditions(new AnyDefendingUnit(unit -> unit.flying))),
          spell("Levitate", 2)
              .desc("Add 2 power, give a unit Flying")
              .addOnEnterEffects(
                  new AdjustPower(2), new SetFlying(true).setTargetFunction(new Inputs())),
          spell("Arcane Defense", 3)
              .desc("Draw 1 card, give two units -1 atk")
              .addOnEnterEffects(
                  new DrawCards(1),
                  new AdjustStrength(-1).setTargetFunction(new Inputs()),
                  new AdjustStrength(-1).setTargetFunction(new Inputs())),
          spell("Lightning Storm", 2)
              .desc("Voidbind, destroy all attacking units with 2 or less atk")
              .addOnEnterEffects(
                  voidBind(),
                  new CreateAction(
                      new DestroyUnit().setTargetFunction(new AttackingMaxStrength(2)))),
          spell("Bolster", 3)
              .desc("Add 2 power, give a unit +3 def")
              .addOnEnterEffects(
                  new AdjustPower(2), new AdjustDefense(3).setTargetFunction(new Inputs())),
          spell("Hipshot", 3)
              .desc("Add 1 power, deal 3 damage to a player")
              .addOnEnterEffects(
                  new AdjustPower(1), new AdjustHp(-3).setTargetFunction(new Inputs())),
          spell("Wisdom of the Elders", 5).desc("Draw 2 cards").addOnEnterEffects(new DrawCards(2)),
          spell("Pack Hunt", 4)
              .desc(
                  "Add 2 power, give all defending units +2 atk if you generated at least 7 power")
              .addOnEnterEffects(new AdjustPower(2))
              .addOnConditionEffects(
                  new AdjustStrength(2)
                      .setTargetFunction(new DefendingUnits())
                      .addWorldConditions(new MinPower(7))),
          spell("Deathstrike", 3)
              .desc("Add 1 power, destroy a unit")
              .addOnEnterEffects(
                  new AdjustPower(1), new DestroyUnit().setTargetFunction(new Inputs())));

  public static List<CommandBuilder> FORGE_CARDS =
      Stream.of(UNIT_CARDS, SPELL_CARDS).flatMap(Collection::stream).collect(Collectors.toList());

  public static CreateEntity spell(String name, int cost) {
    return new CreateCard(name, cost).addTag(Spell.class);
  }

  public static AbstractCommandBuilder voidBind() {
    return new DeleteCard().addTargetConditions(new PlayAreaOrDiscardPile());
  }

  public static class UnitBuilder {
    private final CreateUnit createUnit;
    private final CreateEntity createCard;

    private UnitBuilder(String name, int cost, int strength) {
      this.createUnit = new CreateUnit(name, strength);
      this.createCard = new CreateCard(name, cost).addOnEnterEffects(this.createUnit);
    }

    public static UnitBuilder unit(String name, int cost, int strength) {
      return new UnitBuilder(name, cost, strength);
    }

    public UnitBuilder desc(String description) {
      this.createCard.desc(description);
      this.createUnit.desc(description);
      return this;
    }

    public UnitBuilder addOnEnterEffects(CommandBuilder... builders) {
      this.createUnit.addOnEnterEffects(builders);
      return this;
    }

    public UnitBuilder addOnLeaveEffects(CommandBuilder... builders) {
      this.createUnit.addOnLeaveEffects(builders);
      return this;
    }

    public UnitBuilder addOnConditionEffects(CommandBuilder... builders) {
      this.createUnit.addOnConditionEffects(builders);
      return this;
    }

    public CreateEntity build() {
      return this.createCard;
    }
  }
}

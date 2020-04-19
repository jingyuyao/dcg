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
import com.dcg.source.AttackingMaxStrength;
import com.dcg.source.AttackingUnits;
import com.dcg.source.DefendingMaxStrength;
import com.dcg.source.DefendingUnits;
import com.dcg.source.Inputs;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cards {
  public static List<CommandBuilder> BASIC_CARDS =
      Arrays.asList(
          unit("Eager Owlet", 0, 2).addOnEnterEffects(new SetFlying(true)).build(),
          unit("Awakened Student", 0, 2)
              .addOnConditionEffects(
                  new AdjustStrength(2).addWorldConditions(new MinAnyDefendingStrength(4)))
              .build(),
          unit("Storm Lynx", 0, 1)
              .addOnConditionEffects(
                  new AdjustStrength(2).addWorldConditions(new AnySpell()),
                  new SetEndurance(true).addWorldConditions(new AnySpell()))
              .build(),
          unit("Grenadin Drone", 0, 2).addOnEnterEffects(new CreateUnit("Grenadin", 1)).build(),
          new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Secret Pages", 0).addOnEnterEffects(new AdjustPower(2)));

  @SuppressWarnings("SpellCheckingInspection")
  public static List<CommandBuilder> BASIC_UNITS =
      Arrays.asList(
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
                  .addOnEnterEffects(new SetFlying(true).setTargetFunction(new Inputs())),
              unit("Amethyst Acolyte", 3, 2)
                  .addOnEnterEffects(new AdjustStrength(-2).setTargetFunction(new Inputs())),
              unit("Tinker Apprentice", 1, 1)
                  .addOnEnterEffects(
                      new AdjustPower(1), new AdjustStrength(1).setTargetFunction(new Inputs())),
              unit("Throne Warden", 4, 2)
                  .addOnEnterEffects(
                      new SetEndurance(true), new AdjustHp(new TotalAttackingUnits())),
              unit("Yeti Windflyer", 0, 1)
                  .addOnEnterEffects(new SetFlying(true))
                  .addOnConditionEffects(
                      new AdjustHp(-2)
                          .setTargetFunction(new Inputs())
                          .addWorldConditions(new MinUnitCount(1))),
              unit("Stone shaker", 4, 1)
                  .addOnEnterEffects(new AdjustPower(2), new SetBerserk(true))
                  .addOnConditionEffects(new AdjustStrength(2).addWorldConditions(new MinPower(7))),
              unit("Temple Scribe", 1, 1)
                  .addOnEnterEffects(new AdjustPower(1))
                  .addOnConditionEffects(new DrawCards(1).addWorldConditions(new AnySpell())),
              unit("Mystic Ascendant", 6, 4)
                  .addOnEnterEffects(new DrawCards(1))
                  .addOnConditionEffects(new AdjustStrength(2).addWorldConditions(new MinPower(7))),
              unit("Splimespitter Slug", 5, 3)
                  .addOnEnterEffects(
                      new AdjustPower(1),
                      new AdjustStrength(1).setTargetFunction(new AttackingUnits())),
              unit("Impending Doom", 4, 5).addOnEnterEffects(new SetFlying(true), new AdjustHp(-1)),
              unit("Ridgeline Watcher", 4, 3).addOnEnterEffects(new AdjustDefense(2), voidBind()),
              unit("Pokpok, Rockpacker", 3, 1)
                  .addOnEnterEffects(
                      new DrawCards(1), new AdjustHp(-1).setTargetFunction(new Inputs())),
              unit("Snowrager", 2, 1)
                  .addOnEnterEffects(new SetBerserk(true))
                  .addOnConditionEffects(
                      new AdjustStrength(1).addWorldConditions(new MinUnitCount(3))),
              unit("Cabal Spymaster", 3, 1)
                  .addOnEnterEffects(
                      new SetUnblockable(true),
                      new CreateAction(
                          new SetUnblockable(true).setTargetFunction(new DefendingMaxStrength(3)))),
              unit("Oni Ronin", 1, 1)
                  .addOnEnterEffects(
                      new AdjustStrength(1).setTargetFunction(new Inputs()),
                      new AdjustStrength(1).setTargetFunction(new Inputs())))
          .map(UnitBuilder::build)
          .collect(Collectors.toList());

  public static List<CommandBuilder> SPELL_CARDS =
      Arrays.asList(
          spell("Read the Stars", 2)
              .addOnEnterEffects(new DrawCards(1))
              .addOnConditionEffects(
                  voidBind().addWorldConditions(new AnyDefendingUnit(unit -> unit.flying))),
          spell("Levitate", 2)
              .addOnEnterEffects(
                  new AdjustPower(2), new SetFlying(true).setTargetFunction(new Inputs())),
          spell("Arcane Defense", 3)
              .addOnEnterEffects(
                  new DrawCards(1),
                  new AdjustStrength(-1).setTargetFunction(new Inputs()),
                  new AdjustStrength(-1).setTargetFunction(new Inputs())),
          spell("Lightning Storm", 2)
              .addOnEnterEffects(
                  voidBind(),
                  new CreateAction(
                      new DestroyUnit().setTargetFunction(new AttackingMaxStrength(2)))),
          spell("Bolster", 3)
              .addOnEnterEffects(
                  new AdjustPower(2), new AdjustDefense(3).setTargetFunction(new Inputs())),
          spell("Hipshot", 3)
              .addOnEnterEffects(
                  new AdjustPower(1), new AdjustHp(-3).setTargetFunction(new Inputs())),
          spell("Wisdom of the Elders", 5).addOnEnterEffects(new DrawCards(2)),
          spell("Pack Hunt", 4)
              .addOnEnterEffects(new AdjustPower(2))
              .addOnConditionEffects(
                  new AdjustStrength(2)
                      .setTargetFunction(new DefendingUnits())
                      .addWorldConditions(new MinPower(7))),
          spell("Deathstrike", 3)
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

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
import com.dcg.condition.AnySpell;
import com.dcg.condition.MinAnyUnitStrength;
import com.dcg.condition.MinPower;
import com.dcg.condition.MinUnitCount;
import com.dcg.condition.PlayAreaOrDiscardPile;
import com.dcg.player.AdjustHp;
import com.dcg.player.AdjustPower;
import com.dcg.player.DrawCards;
import com.dcg.targetsource.AttackingMaxStrength;
import com.dcg.targetsource.AttackingUnits;
import com.dcg.targetsource.DefendingMaxStrength;
import com.dcg.targetsource.DefendingUnits;
import com.dcg.targetsource.Inputs;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Cards {
  public static List<CommandBuilder> BASIC_CARDS =
      Arrays.asList(
          new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Diplomacy", 0).addOnEnterEffects(new AdjustPower(1)),
          new CreateCard("Worn Shield", 0)
              .addOnEnterEffects(new AdjustDefense(2).setTargetSource(new Inputs())),
          new CreateCard("Secret Pages", 0).addOnEnterEffects(new AdjustPower(2)));

  @SuppressWarnings("SpellCheckingInspection")
  public static List<CommandBuilder> BASIC_UNITS =
      Arrays.asList(
          new CreateCard("Yeti Windflyer", 0)
              .addOnEnterEffects(
                  new CreateUnit("Yeti Windflyer", 1)
                      .addOnEnterEffects(new SetFlying(true))
                      .addOnConditionEffects(
                          new AdjustHp(-2)
                              .setTargetSource(new Inputs())
                              .addWorldConditions(new MinUnitCount(1)))),
          new CreateCard("Awakened Student", 0)
              .addOnEnterEffects(
                  new CreateUnit("Awakened Student", 2)
                      .addOnConditionEffects(
                          new AdjustStrength(2).addWorldConditions(new MinAnyUnitStrength(4)))),
          new CreateCard("Eager Owlet", 0)
              .addOnEnterEffects(
                  new CreateUnit("Eager Owlet", 2).addOnEnterEffects(new SetFlying(true))),
          new CreateCard("Fearless Nomad", 0)
              .addOnEnterEffects(
                  new CreateUnit("Fearless Nomad", 2).addOnEnterEffects(new SetBerserk(true))),
          new CreateCard("Stonepowder Alchemist", 0)
              .addOnEnterEffects(
                  new CreateUnit("Stonepowder Alchemist", 2)
                      .addOnEnterEffects(new SetLifeSteal(true))),
          new CreateCard("Storm Lynx", 0)
              .addOnEnterEffects(
                  new CreateUnit("Storm Lynx", 1)
                      .addOnConditionEffects(
                          new AdjustStrength(2).addWorldConditions(new AnySpell()),
                          new SetEndurance(true).addWorldConditions(new AnySpell()))),
          new CreateCard("Xenan Cupbearer", 0)
              .addOnEnterEffects(
                  new CreateUnit("Xenan Cupbearer", 1)
                      .addOnEnterEffects(new AdjustDefense(1), new AdjustHp(1))),
          new CreateCard("Grenadin Drone", 0)
              .addOnEnterEffects(
                  new CreateUnit("Grenadin Drone", 2), new CreateUnit("Grenadin", 1)),
          new CreateCard("Withering Witch", 0)
              .addOnEnterEffects(
                  new CreateUnit("Withering Witch", 2).addOnEnterEffects(new AdjustDefense(3))));

  @SuppressWarnings("SpellCheckingInspection")
  public static List<CommandBuilder> FORGE_CARDS =
      Arrays.asList(
          new CreateCard("Deathstrike", 3)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(
                  new AdjustPower(1), new DestroyUnit().setTargetSource(new Inputs())),
          new CreateCard("Wisdom of the Elders", 5)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(new DrawCards(2)),
          new CreateCard("Pack Hunt", 4)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(new AdjustPower(2))
              .addOnConditionEffects(
                  new AdjustStrength(2)
                      .setTargetSource(new DefendingUnits())
                      .addWorldConditions(new MinPower(7))),
          new CreateCard("Temple Scribe", 0)
              .addOnEnterEffects(
                  new CreateUnit("Storm Lynx", 1)
                      .addOnEnterEffects(new AdjustPower(1))
                      .addOnConditionEffects(new DrawCards(1).addWorldConditions(new AnySpell()))),
          new CreateCard("Mystic Ascendant", 6)
              .addOnEnterEffects(
                  new CreateUnit("Mystic Ascendant", 4)
                      .addOnEnterEffects(new DrawCards(1))
                      .addOnConditionEffects(
                          new AdjustStrength(2).addWorldConditions(new MinPower(7)))),
          new CreateCard("Levitate", 2)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(
                  new AdjustPower(2), new SetFlying(true).setTargetSource(new Inputs())),
          new CreateCard("Arcane Defense", 3)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(
                  new DrawCards(1),
                  new AdjustStrength(-1).setTargetSource(new Inputs()),
                  new AdjustStrength(-1).setTargetSource(new Inputs())),
          new CreateCard("Lightning Storm", 2)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(
                  new CreateAction(
                      new DeleteCard().addTargetConditions(new PlayAreaOrDiscardPile())),
                  new CreateAction(new DestroyUnit().setTargetSource(new AttackingMaxStrength(2)))),
          new CreateCard("Bolster", 3)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(
                  new AdjustPower(2), new AdjustDefense(3).setTargetSource(new Inputs())),
          new CreateCard("Splimespitter Slug", 5)
              .addOnEnterEffects(
                  new CreateUnit("Splimespitter Slug", 3)
                      .addOnEnterEffects(
                          new AdjustPower(1),
                          new AdjustStrength(1).setTargetSource(new AttackingUnits()))),
          new CreateCard("Impending Doom", 4)
              .addOnEnterEffects(
                  new CreateUnit("Impending Doom", 5)
                      .addOnEnterEffects(new SetFlying(true), new AdjustHp(-11))),
          new CreateCard("Ridgeline Watcher", 4)
              .addOnEnterEffects(
                  new CreateUnit("Ridgeline Watcher", 3)
                      .addOnEnterEffects(
                          new AdjustDefense(2),
                          new CreateAction(
                              new DeleteCard().addTargetConditions(new PlayAreaOrDiscardPile())))),
          new CreateCard("Pokpok, Rockpacker", 3)
              .addOnEnterEffects(
                  new CreateUnit("Pokpok, Rockpacker", 1)
                      .addOnEnterEffects(
                          new DrawCards(1), new AdjustHp(-1).setTargetSource(new Inputs()))),
          new CreateCard("Snowrager", 2)
              .addOnEnterEffects(
                  new CreateUnit("Snowrager", 1)
                      .addOnEnterEffects(new SetBerserk(true))
                      .addOnConditionEffects(
                          new AdjustStrength(1).addWorldConditions(new MinUnitCount(3)))),
          new CreateCard("Cabal Spymaster", 3)
              .addOnEnterEffects(
                  new CreateUnit("Cabal Spymaster", 1)
                      .addOnEnterEffects(
                          new SetUnblockable(true),
                          new CreateAction(
                              new SetUnblockable(true)
                                  .setTargetSource(new DefendingMaxStrength(3))))),
          new CreateCard("Oni Ronin", 1)
              .addOnEnterEffects(
                  new CreateUnit("Oni Ronin", 1)
                      .addOnEnterEffects(
                          new AdjustStrength(1).setTargetSource(new Inputs()),
                          new AdjustStrength(1).setTargetSource(new Inputs()))));
}

package com.dcg.card;

import com.dcg.battle.AdjustDefense;
import com.dcg.battle.AdjustStrength;
import com.dcg.battle.CreateUnit;
import com.dcg.battle.DestroyUnit;
import com.dcg.battle.SetBerserk;
import com.dcg.battle.SetEndurance;
import com.dcg.battle.SetFlying;
import com.dcg.battle.SetLifeSteal;
import com.dcg.command.CommandBuilder;
import com.dcg.effect.AnySpell;
import com.dcg.effect.MinAnyUnitStrength;
import com.dcg.effect.MinUnitCount;
import com.dcg.player.AdjustHp;
import com.dcg.player.AdjustPower;
import com.dcg.player.DrawCards;
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
          new CreateCard("Worn Shield", 0).addOnEnterEffects(new AdjustDefense(2)),
          new CreateCard("Secret Pages", 0).addOnEnterEffects(new AdjustPower(2)));

  @SuppressWarnings("SpellCheckingInspection")
  public static List<CommandBuilder> BASIC_UNITS =
      Arrays.asList(
          new CreateCard("Yeti Windflyer", 0)
              .addOnEnterEffects(
                  new CreateUnit("Yeti Windflyer", 1)
                      .addOnEnterEffects(new SetFlying(true))
                      .addOnConditionEffects(
                          new AdjustHp(-2, false).addCondition(new MinUnitCount(1)))),
          new CreateCard("Awakened Student", 0)
              .addOnEnterEffects(
                  new CreateUnit("Awakened Student", 2)
                      .addOnConditionEffects(
                          new AdjustStrength(2).addCondition(new MinAnyUnitStrength(4)))),
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
                          new AdjustStrength(2).addCondition(new AnySpell()),
                          new SetEndurance(true).addCondition(new AnySpell()))),
          new CreateCard("Xenan Cupbearer", 0)
              .addOnEnterEffects(
                  new CreateUnit("Xenan Cupbearer", 1).addOnEnterEffects(new AdjustDefense(1)),
                  new AdjustHp(1, true)),
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
              .addOnEnterEffects(new AdjustPower(1), new DestroyUnit()),
          new CreateCard("Wisdom of the Elders", 5)
              .addTags(Collections.singletonList(Spell.class))
              .addOnEnterEffects(new DrawCards(2)),
          new CreateCard("Oni Ronin", 1)
              .addOnEnterEffects(
                  new CreateUnit("Oni Ronin", 1), new AdjustStrength(1), new AdjustStrength(1)));
}

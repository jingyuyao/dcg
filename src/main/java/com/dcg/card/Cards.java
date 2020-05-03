package com.dcg.card;

import static com.dcg.action.CreateAction.action;
import static com.dcg.battle.CreateUnit.unitToken;
import static com.dcg.card.CreateCard.basic;
import static com.dcg.card.CreateCard.spell;
import static com.dcg.card.CreateCard.unit;
import static com.dcg.card.CreateCard.unitNoBlock;

import com.dcg.battle.AdjustDefense;
import com.dcg.battle.AdjustStrength;
import com.dcg.battle.DestroyUnit;
import com.dcg.battle.SetBerserk;
import com.dcg.battle.SetEndurance;
import com.dcg.battle.SetFlying;
import com.dcg.battle.SetLifeSteal;
import com.dcg.battle.SetUnblockable;
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
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(new AdjustPower(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(new AdjustPower(1)),
        basic("Refresh")
            .desc("Throne: refresh your Wrap tokens")
            .addOnConditionEffects(
                action("Refresh", new RefreshWrapTokens())
                    .desc("Refresh your Wrap tokens")
                    .addTriggerConditions(new ThroneActive())),
        basic("Worn Shield")
            .desc("Throne: give a Unit +2 Defense")
            .addOnConditionEffects(
                action(
                        "+2 Defense",
                        new AdjustDefense(2).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit +2 Defense")
                    .addTriggerConditions(new ThroneActive())),
        basic("Secret Pages").desc("Add 2 Power").addOnEnterEffects(new AdjustPower(2)));
  }

  public static List<CreateEntity> createBasicUnits() {
    return Arrays.asList(
        unit("Yeti Windflyer", 4, 1)
            .tags(Red.class, Blue.class)
            .desc("Flying; deal 2 damage to a Player if you are 3 or more Units")
            .addOnEnterEffects(new SetFlying(true))
            .addOnConditionEffects(
                dealDamage(2).addTriggerConditions(new MinDefendingUnitCount(3))),
        unit("Noble Firemane", 0, 2)
            .tags(Red.class, Yellow.class)
            .desc("Throne: +3 Strength")
            .addOnConditionEffects(new AdjustStrength(3).addTriggerConditions(new ThroneActive())),
        unit("Eager Owlet", 0, 2)
            .tags(Green.class, Blue.class)
            .desc("Flying")
            .addOnEnterEffects(new SetFlying(true)),
        unit("Awakened Student", 0, 2)
            .tags(Yellow.class, Green.class)
            .desc("+2 Strength if you have a Unit with 4 Strength or more")
            .addOnConditionEffects(
                new AdjustStrength(2)
                    .addTriggerConditions(new AnyDefendingUnit(unit -> unit.strength >= 4))),
        unit("Storm Lynx", 0, 1)
            .tags(Yellow.class, Blue.class)
            .desc("+2 Strength and Endurance if you played a Spell this turn")
            .addOnConditionEffects(
                new AdjustStrength(2).addTriggerConditions(new PlayedTag(Spell.class)),
                new SetEndurance(true).addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Grenadin Drone", 0, 2)
            .tags(Red.class, Black.class)
            .desc("Create a 1 Strength Grenadin")
            .addOnEnterEffects(grenadin()),
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
            .desc("1 Defense; gain 1 HP")
            .addOnEnterEffects(new AdjustDefense(1), new AdjustHp(1)),
        unit("Withering Witch", 0, 2)
            .tags(Blue.class, Black.class)
            .desc("3 Defense")
            .addOnEnterEffects(new AdjustDefense(3)));
  }

  public static List<CreateEntity> createThroneDeck(int numPlayers) {
    List<CreateEntity> seekPowers = new ArrayList<>();
    for (int i = 0; i < numPlayers * 2; i++) {
      seekPowers.add(
          spell("Seek Power", 3)
              .desc("Add 2 Power; Throne: may banish this to create a 2 Strength Cavalry")
              .addOnEnterEffects(new AdjustPower(2))
              .addOnConditionEffects(
                  action("Banish", new DeleteCard().chain(unitToken("Cavalry", 2)))
                      .desc("Banish this to create a 2 Strength Cavalry")
                      .addTriggerConditions(new ThroneActive())));
    }
    return seekPowers;
  }

  public static List<CreateEntity> createMercenaries() {
    List<CreateEntity> mercenaries = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      mercenaries.add(
          unit("Veteran Mercenary", 2, 2)
              .desc("Add 1 Power; can become any 1 color")
              .addOnEnterEffects(new AdjustPower(1), new MercenaryEnter())
              .addOnLeaveEffects(new MercenaryExit()));
    }
    return mercenaries;
  }

  public static List<CreateEntity> createForgeDeck() {
    return Arrays.asList(
        unit("Beckoning Lumen", 3, 3)
            .tags(Yellow.class)
            .desc("Add 1 Power; Yellow: gain 2 HP")
            .addOnEnterEffects(new AdjustPower(1))
            .addOnConditionEffects(
                new AdjustHp(2).addTriggerConditions(new PlayedTag(Yellow.class))),
        unit("Jotun Punter", 4, 4)
            .canWrap()
            .tags(Blue.class)
            .desc("Give a Unit Flying")
            .addOnEnterEffects(
                action(
                        "Give Flying",
                        new SetFlying(true).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit Flying")),
        unit("Amethyst Acolyte", 3, 2)
            .canWrap()
            .tags(Black.class)
            .desc("Give a Unit -2 Strength")
            .addOnEnterEffects(
                action(
                        "-2 Strength",
                        new AdjustStrength(-2).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit -2 Strength")),
        unit("Tinker Apprentice", 1, 1)
            .tags(Green.class)
            .desc("Add 1 Power; give a Unit +1 Strength")
            .addOnEnterEffects(new AdjustPower(1), giveBuff(1)),
        unit("Throne Warden", 4, 2)
            .canWrap()
            .tags(Green.class)
            .desc("Endurance; gain HP equal to number of attacking Units")
            .addOnEnterEffects(new SetEndurance(true), new AdjustHp(new TotalAttackingUnits())),
        unit("Stoneshaker", 4, 1)
            .tags(Red.class)
            .desc("Add 2 Power; Berserk; add 2 Strength to generated 7 or more Power")
            .addOnEnterEffects(new AdjustPower(2), new SetBerserk(true))
            .addOnConditionEffects(new AdjustStrength(2).addTriggerConditions(new MinPower(7))),
        unit("Temple Scribe", 1, 1)
            .tags(Yellow.class)
            .desc("Add 1 Power; draw 1 card if you played any Spell")
            .addOnEnterEffects(new AdjustPower(1))
            .addOnConditionEffects(
                new DrawCards(1).addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Mystic Ascendant", 6, 4)
            .tags(Yellow.class)
            .desc("Draw 1 card; add 2 Strength to this if you generated 7 or more Power")
            .addOnEnterEffects(new DrawCards(1))
            .addOnConditionEffects(new AdjustStrength(2).addTriggerConditions(new MinPower(7))),
        unit("Splimespitter Slug", 5, 3)
            .tags(Green.class, Black.class)
            .desc("Add 1 Power; give all attacking Units -1 Strength")
            .addOnEnterEffects(
                new AdjustPower(1), new AdjustStrength(-1).setTargetSource(new AttackingUnits())),
        unit("Impending Doom", 4, 5)
            .tags(Black.class)
            .desc("Flying; minus 1 HP")
            .addOnEnterEffects(new SetFlying(true), new AdjustHp(-1)),
        unit("Ridgeline Watcher", 4, 3)
            .tags(Blue.class)
            .desc("Add 2 Defense; Voidbind")
            .addOnEnterEffects(new AdjustDefense(2), voidbind()),
        unit("Pokpok, Rockpacker", 3, 1)
            .tags(Red.class, Blue.class)
            .desc("Draw 1 card; give a Unit -1 Strength")
            .addOnEnterEffects(new DrawCards(1), giveDebuff(1)),
        unit("Snowrager", 2, 1)
            .canWrap()
            .tags(Blue.class)
            .desc("Berserk; add 1 Strength to this if you have 3 or more Units")
            .addOnEnterEffects(new SetBerserk(true))
            .addOnConditionEffects(
                new AdjustStrength(1).addTriggerConditions(new MinDefendingUnitCount(3))),
        unit("Cabal Spymaster", 3, 1)
            .tags(Black.class)
            .desc("Unblockable; give all defending Units with 3 or less Strength Unblockable")
            .addOnEnterEffects(
                new SetUnblockable(true),
                action(
                        "Give Unblockable",
                        new SetUnblockable(true)
                            .setTargetSource(new DefendingUnits().addFilters(new MaxStrength(3))))
                    .desc("Give all defending Units with 3 or less Strength Unblockable")),
        unit("Oni Ronin", 1, 1)
            .canWrap()
            .tags(Red.class)
            .desc("Add 1 Strength to two Units")
            .addOnEnterEffects(
                action(
                        "+1 Strength",
                        new AdjustStrength(1).setInputCount(1, 2).setTargetSource(new AllUnits()))
                    .desc("Add 1 Strength to two Units")),
        unit("Treasury Guard", 4, 2)
            .tags(Green.class)
            .desc("Add 2 Power; Green: +1 Defense and Endurance")
            .addOnEnterEffects(new AdjustPower(2))
            .addOnConditionEffects(
                new AdjustDefense(1).addTriggerConditions(new PlayedTag(Green.class)),
                new SetEndurance(true).addTriggerConditions(new PlayedTag(Green.class))),
        unit("Iceberg Warchief", 3, 3)
            .tags(Red.class, Blue.class)
            .desc("Red: create a 2 Strength Yeti; Blue: create a 2 Strength Yeti")
            .addOnConditionEffects(
                yeti().addTriggerConditions(new PlayedTag(Red.class)),
                yeti().addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Lumbering Gruan", 3, 3)
            .tags(Blue.class)
            .desc("Berserk; Blue: Add 2 Power")
            .addOnEnterEffects(new SetBerserk(true))
            .addOnConditionEffects(
                new AdjustPower(2).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Midnight Gale", 4, 3)
            .tags(Blue.class, Black.class)
            .desc("Add 1 Power; Blue: Flying; Black: Lifesteal")
            .addOnEnterEffects(new AdjustPower(1))
            .addOnConditionEffects(
                new SetFlying(true).addTriggerConditions(new PlayedTag(Blue.class)),
                new SetLifeSteal(true).addTriggerConditions(new PlayedTag(Black.class))),
        unit("Cabal Cutthroat", 4, 2)
            .tags(Black.class)
            .desc("Add 2 Power; Lifesteal; Throne: Banish a card in the Forge Row")
            .addOnEnterEffects(new AdjustPower(2), new SetLifeSteal(true))
            .addOnConditionEffects(banishFromForgeRow().addTriggerConditions(new ThroneActive())),
        unit("Vampire Bat", 2, 1)
            .tags(Black.class)
            .desc("Add 1 Power; Flying; Lifesteal; Black: +1 Strength")
            .addOnEnterEffects(new AdjustPower(1), new SetFlying(true), new SetLifeSteal(true))
            .addOnConditionEffects(
                new AdjustStrength(1).addTriggerConditions(new PlayedTag(Black.class))),
        unit("Back-Alley Bouncer", 5, 3)
            .tags(Black.class)
            .desc("Voidbind; Black: Destroy an Unit")
            .addOnEnterEffects(voidbind())
            .addOnConditionEffects(destroyUnit().addTriggerConditions(new PlayedTag(Black.class))),
        unit("Blistersting Wasp", 2, 2)
            .canWrap()
            .tags(Yellow.class, Black.class)
            .desc("Flying; Yellow: Endurance; Black: +5 Defense")
            .addOnEnterEffects(new SetFlying(true))
            .addOnConditionEffects(
                new SetEndurance(true).addTriggerConditions(new PlayedTag(Yellow.class)),
                new AdjustDefense(5).addTriggerConditions(new PlayedTag(Black.class))),
        unit("Dune Phantom", 2, 1)
            .canWrap()
            .tags(Yellow.class)
            .desc("+5 Defense; Throne: Flying")
            .addOnEnterEffects(new AdjustDefense(5))
            .addOnConditionEffects(new SetFlying(true).addTriggerConditions(new ThroneActive())),
        unit("Steelbound Dragon", 6, 3)
            .tags(Red.class)
            .desc("Add 2 Power; Flying; Throne: +3 Strength")
            .addOnEnterEffects(new AdjustPower(2), new SetFlying(true))
            .addOnConditionEffects(new AdjustStrength(3).addTriggerConditions(new ThroneActive())),
        unit("Heart of the Vault", 6, 5)
            .tags(Red.class, Yellow.class)
            .desc("Red: add 2 Power; Yellow: draw a card")
            .addOnConditionEffects(
                new AdjustPower(2).addTriggerConditions(new PlayedTag(Red.class)),
                new DrawCards(1).addTriggerConditions(new PlayedTag(Yellow.class))),
        unit("Eilyn, Queen of the Wilds", 6, 4)
            .canWrap()
            .tags(Blue.class)
            .desc("Flying; Give all defending units Flying")
            .addOnEnterEffects(
                new SetFlying(true),
                action("Give Flying", new SetFlying(true).setTargetSource(new DefendingUnits()))
                    .desc("Give Flying to all defending units")),
        unit("Corrupted Umbren", 3, 3)
            .canWrap()
            .tags(Black.class)
            .desc("+2 Defense; Lifesteal")
            .addOnEnterEffects(new AdjustDefense(2), new SetLifeSteal(true)),
        unitNoBlock("Horned Vorlunk", 2, 4).canWrap().tags(Yellow.class).desc("Can't Block"),
        unit("Icicle Marksman", 3, 2)
            .canWrap()
            .tags(Blue.class)
            .desc("Deals 3 damage if you played a spell")
            .addOnConditionEffects(dealDamage(3).addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Explorer Emeritus", 4, 1)
            .tags(Yellow.class, Blue.class)
            .desc("Add 1 Power; Yellow: draw a card; Blue: draw a card")
            .addOnConditionEffects(
                new DrawCards(1).addTriggerConditions(new PlayedTag(Yellow.class)),
                new DrawCards(1).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("East-Wind Herald", 2, 1)
            .tags(Blue.class)
            .desc("Flying; +3 Defense; Blue: draw a card")
            .addOnEnterEffects(new SetFlying(true), new AdjustDefense(3))
            .addOnConditionEffects(
                new DrawCards(1).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Rolant, the Iron Fist", 7, 4)
            .canWrap()
            .tags(Green.class)
            .desc("Endurance; Give Endurance to all defending units")
            .addOnEnterEffects(
                new SetEndurance(true),
                action(
                        "Give Endurance",
                        new SetEndurance(true).setTargetSource(new DefendingUnits()))
                    .desc("Give Endurance to all defending units")),
        unit("Vara, Vengeance-Seeker", 7, 4)
            .canWrap()
            .tags(Black.class)
            .desc("Lifesteal; Unblockable")
            .addOnEnterEffects(new SetLifeSteal(true), new SetUnblockable(true)),
        unit("Sandstorm Titan", 5, 5)
            .canWrap()
            .tags(Yellow.class)
            .desc("Remove Flying from all units; Yellow: Endurance")
            .addOnEnterEffects(
                action("Remove Flying", new SetFlying(false).setTargetSource(new AllUnits()))
                    .desc("Remove Flying from all units"))
            .addOnConditionEffects(
                new SetEndurance(true).addTriggerConditions(new PlayedTag(Yellow.class))),
        unit("Renegade Valkyrie", 1, 1)
            .tags(Red.class, Green.class)
            .desc("Flying; Berserk; Red: +1 Strength; Green: +1 Strength")
            .addOnEnterEffects(new SetFlying(true), new SetBerserk(true))
            .addOnConditionEffects(
                new AdjustStrength(1).addTriggerConditions(new PlayedTag(Red.class)),
                new AdjustStrength(1).addTriggerConditions(new PlayedTag(Green.class))),
        unit("Champion of Progress", 5, 4)
            .tags(Yellow.class, Green.class)
            .desc("Add 1 Power; Yellow: Endurance; Green: +1 Strength")
            .addOnEnterEffects(new AdjustPower(1))
            .addOnConditionEffects(
                new SetEndurance(true).addTriggerConditions(new PlayedTag(Yellow.class)),
                new AdjustStrength(1).addTriggerConditions(new PlayedTag(Green.class))),
        unitNoBlock("Xenan Destroyer", 3, 3)
            .tags(Black.class)
            .desc("Add 1 Power; Lifesteal; Can't Block; Throne: +1 Strength")
            .addOnEnterEffects(new AdjustPower(1), new SetLifeSteal(true))
            .addOnConditionEffects(new AdjustStrength(1).addTriggerConditions(new ThroneActive())),
        spell("Brilliant Discovery", 5)
            .tags(Red.class, Yellow.class)
            .desc("Add 3 Power; Throne: banish a card in the Forge Row")
            .addOnEnterEffects(new AdjustPower(3))
            .addOnConditionEffects(banishFromForgeRow().addTriggerConditions(new ThroneActive())),
        spell("Unfinished Business", 4)
            .tags(Green.class, Black.class)
            .desc("Add 2 Power; Green: give a Unit +2 Strength; Black: give a Unit -2 Strength")
            .addOnEnterEffects(new AdjustPower(2))
            .addOnConditionEffects(
                giveBuff(2).addTriggerConditions(new PlayedTag(Green.class)),
                giveDebuff(2).addTriggerConditions(new PlayedTag(Black.class))),
        spell("Assembly Line", 3)
            .tags(Red.class)
            .desc("Add 2 Power; Red: create two 1 Strength Grenadin")
            .addOnEnterEffects(new AdjustPower(2))
            .addOnConditionEffects(
                grenadin().addTriggerConditions(new PlayedTag(Red.class)),
                grenadin().addTriggerConditions(new PlayedTag(Red.class))),
        spell("Scouting Party", 5)
            .canWrap()
            .tags(Blue.class)
            .desc("Create two 2 Strength Yeti; Throne: create another 2 Strength Yeti")
            .addOnEnterEffects(yeti(), yeti())
            .addOnConditionEffects(yeti().addTriggerConditions(new ThroneActive())),
        spell("Xenan Augury", 2)
            .tags(Black.class)
            .desc("Voidbind; Throne: gain 2 HP")
            .addOnEnterEffects(voidbind())
            .addOnConditionEffects(new AdjustHp(2).addTriggerConditions(new ThroneActive())),
        spell("Read the Stars", 2)
            .tags(Green.class, Blue.class)
            .desc("Draw 1 card; Voidbind if you have a Flying Unit")
            .addOnEnterEffects(new DrawCards(1))
            .addOnConditionEffects(
                voidbind().addTriggerConditions(new AnyDefendingUnit(unit -> unit.flying))),
        spell("Levitate", 2)
            .tags(Blue.class)
            .desc("Add 2 Power; give a Unit Flying")
            .addOnEnterEffects(
                new AdjustPower(2),
                action(
                        "Give Flying",
                        new SetFlying(true).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit Flying")),
        spell("Arcane Defense", 3)
            .tags(Green.class)
            .desc("Draw 1 card; give two Units -1 Strength")
            .addOnEnterEffects(
                new DrawCards(1),
                action(
                        "-1 Strength",
                        new AdjustStrength(-1).setInputCount(1, 2).setTargetSource(new AllUnits()))
                    .desc("Give two Units -1 Strength")),
        spell("Lightning Storm", 2)
            .tags(Blue.class)
            .desc("Voidbind; destroy all attacking Units with 2 or less Strength")
            .addOnEnterEffects(
                voidbind(),
                action(
                        "Destroy Units",
                        new DestroyUnit()
                            .setTargetSource(new AttackingUnits().addFilters(new MaxStrength(2))))
                    .desc("Destroy all attacking Units with 2 or less Strength")),
        spell("Bolster", 3)
            .tags(Yellow.class)
            .desc("Add 2 Power; give a Unit +3 Defense")
            .addOnEnterEffects(
                new AdjustPower(2),
                action(
                        "+3 Defense",
                        new AdjustDefense(3).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit +3 Defense")),
        spell("Hipshot", 3)
            .tags(Red.class)
            .desc("Add 1 Power; deal 3 damage to a Player")
            .addOnEnterEffects(new AdjustPower(1), dealDamage(3)),
        spell("Wisdom of the Elders", 5)
            .tags(Blue.class)
            .desc("Draw 2 cards")
            .addOnEnterEffects(new DrawCards(2)),
        spell("Pack Hunt", 4)
            .tags(Yellow.class)
            .desc(
                "Add 2 Power; give all defending Units +2 Strength if you generated at least 7 Power")
            .addOnEnterEffects(new AdjustPower(2))
            .addOnConditionEffects(
                action("+2 Strength", new AdjustStrength(2).setTargetSource(new DefendingUnits()))
                    .desc("Give all defending Units +2 Strength")
                    .addTriggerConditions(new MinPower(7))),
        spell("Deathstrike", 3)
            .tags(Black.class)
            .desc("Add 1 Power; destroy a Unit")
            .addOnEnterEffects(new AdjustPower(1), destroyUnit()));
  }

  public static CreateEntity voidbind() {
    return action(
            "Voidbind", new DeleteCard().setInputCount(1).setTargetSource(new VoidbindableCards()))
        .desc("Banish a card from your Hand or Discard Pile");
  }

  public static CreateEntity destroyUnit() {
    return action(
            "Destroy Unit", new DestroyUnit().setInputCount(1).setTargetSource(new AllUnits()))
        .desc("Destroy a Unit");
  }

  public static CreateEntity dealDamage(int hp) {
    String str = String.format("Deal %d damage", hp);
    return action(str, new AdjustHp(-hp).setInputCount(1).setTargetSource(new ActivePlayers()))
        .desc(str + " to a Player");
  }

  public static CreateEntity banishFromForgeRow() {
    return action("Banish", new DeleteCard().setInputCount(1).setTargetSource(new ForgeRowCards()))
        .desc("Banish a card from the Forge Row");
  }

  public static CreateEntity giveBuff(int strength) {
    String str = String.format("+%d Strength", strength);
    return action(
            str, new AdjustStrength(strength).setInputCount(1).setTargetSource(new AllUnits()))
        .desc("Give a Unit " + str);
  }

  public static CreateEntity giveDebuff(int strength) {
    String str = String.format("-%d Strength", strength);
    return action(
            str, new AdjustStrength(-strength).setInputCount(1).setTargetSource(new AllUnits()))
        .desc("Give a Unit " + str);
  }

  public static CreateEntity yeti() {
    return unitToken("Yeti", 2);
  }

  public static CreateEntity grenadin() {
    return unitToken("Grenadin", 1);
  }
}

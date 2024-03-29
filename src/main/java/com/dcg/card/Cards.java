package com.dcg.card;

import static com.dcg.action.CreateAction.action;
import static com.dcg.battle.AdjustDefense.defense;
import static com.dcg.battle.AdjustStrength.strength;
import static com.dcg.battle.CreateUnit.createUnitToken;
import static com.dcg.battle.DestroyUnit.destroyUnit;
import static com.dcg.battle.SetBerserk.berserk;
import static com.dcg.battle.SetEndurance.endurance;
import static com.dcg.battle.SetFlying.flying;
import static com.dcg.battle.SetLifeSteal.lifesteal;
import static com.dcg.battle.SetUnblockable.unblockable;
import static com.dcg.card.CreateCard.basic;
import static com.dcg.card.CreateCard.spell;
import static com.dcg.card.CreateCard.unit;
import static com.dcg.card.CreateCard.unitNoBlock;
import static com.dcg.player.AdjustHp.hp;
import static com.dcg.player.AdjustPower.power;
import static com.dcg.player.DrawCards.draw;

import com.dcg.battle.SetFlying;
import com.dcg.commandargs.TotalAttackingUnits;
import com.dcg.game.CreateEntity;
import com.dcg.player.AdjustHp;
import com.dcg.player.RefreshWarpTokens;
import com.dcg.targetfilter.MaxStrength;
import com.dcg.targetfilter.UnitFilter;
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
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(power(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(power(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(power(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(power(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(power(1)),
        basic("Diplomacy").desc("Add 1 Power").addOnEnterEffects(power(1)),
        basic("Refresh")
            .desc("Throne: refresh your Warp tokens")
            .addOnConditionEffects(
                action("Refresh", new RefreshWarpTokens())
                    .desc("Refresh your Warp tokens")
                    .addTriggerConditions(new ThroneActive())),
        basic("Worn Shield")
            .desc("Throne: give a Unit +2 Defense")
            .addOnConditionEffects(
                action("+2 Defense", defense(2).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit +2 Defense")
                    .addTriggerConditions(new ThroneActive())),
        basic("Secret Pages").desc("Add 2 Power").addOnEnterEffects(power(2)));
  }

  public static List<CreateEntity> createBasicUnits() {
    return Arrays.asList(
        unit("Yeti Windflyer", 4, 1)
            .tags(Red.class, Blue.class)
            .desc("Flying; deal 2 damage to a Player if you are 3 or more Units")
            .addOnEnterEffects(flying())
            .addOnConditionEffects(
                dealDamageAction(2).addTriggerConditions(new MinDefendingUnitCount(3))),
        unit("Noble Firemane", 0, 2)
            .tags(Red.class, Yellow.class)
            .desc("Throne: +3 Strength")
            .addOnConditionEffects(strength(3).addTriggerConditions(new ThroneActive())),
        unit("Eager Owlet", 0, 2)
            .tags(Green.class, Blue.class)
            .desc("Flying")
            .addOnEnterEffects(flying()),
        unit("Awakened Student", 0, 2)
            .tags(Yellow.class, Green.class)
            .desc("+2 Strength if you have a Unit with 4 Strength or more")
            .addOnConditionEffects(
                strength(2).addTriggerConditions(new AnyDefendingUnit(unit -> unit.strength >= 4))),
        unit("Storm Lynx", 0, 1)
            .tags(Yellow.class, Blue.class)
            .desc("+2 Strength and Endurance if you played a Spell this turn")
            .addOnConditionEffects(
                strength(2).addTriggerConditions(new PlayedTag(Spell.class)),
                endurance().addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Grenadin Drone", 0, 2)
            .tags(Red.class, Black.class)
            .desc("Create a 1 Strength Grenadin")
            .addOnEnterEffects(createGrenadin()),
        unit("Fearless Nomad", 0, 2)
            .tags(Red.class, Green.class)
            .desc("Berserk")
            .addOnEnterEffects(berserk()),
        unit("Stonepowder Alchemist", 0, 2)
            .tags(Green.class, Black.class)
            .desc("Lifesteal")
            .addOnEnterEffects(lifesteal()),
        unit("Xenan Cupbearer", 0, 1)
            .tags(Yellow.class, Black.class)
            .desc("1 Defense; gain 1 HP")
            .addOnEnterEffects(defense(1), hp(1)),
        unit("Withering Witch", 0, 2)
            .tags(Blue.class, Black.class)
            .desc("3 Defense")
            .addOnEnterEffects(defense(3)));
  }

  public static List<CreateEntity> createThroneDeck(int numPlayers) {
    List<CreateEntity> seekPowers = new ArrayList<>();
    for (int i = 0; i < numPlayers * 2; i++) {
      seekPowers.add(
          spell("Seek Power", 3)
              .desc("Add 2 Power; Throne: may banish this to create a 2 Strength Cavalry")
              .addOnEnterEffects(power(2))
              .addOnConditionEffects(
                  action("Banish", new BanishCard().chain(createUnitToken("Cavalry", 2)))
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
              .addOnEnterEffects(power(1), new MercenaryEnter())
              .addOnLeaveEffects(new MercenaryExit()));
    }
    return mercenaries;
  }

  public static List<CreateEntity> createForgeDeck() {
    return Arrays.asList(
        unit("Beckoning Lumen", 3, 3)
            .tags(Yellow.class)
            .desc("Add 1 Power; Yellow: gain 2 HP")
            .addOnEnterEffects(power(1))
            .addOnConditionEffects(hp(2).addTriggerConditions(new PlayedTag(Yellow.class))),
        unit("Jotun Punter", 4, 4)
            .canWarp()
            .tags(Blue.class)
            .desc("Give a Unit Flying")
            .addOnEnterEffects(
                action("Give Flying", flying().setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit Flying")),
        unit("Amethyst Acolyte", 3, 2)
            .canWarp()
            .tags(Black.class)
            .desc("Give a Unit -2 Strength")
            .addOnEnterEffects(
                action("-2 Strength", strength(-2).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit -2 Strength")),
        unit("Tinker Apprentice", 1, 1)
            .tags(Green.class)
            .desc("Add 1 Power; give a Unit +1 Strength")
            .addOnEnterEffects(power(1), buffAction(1)),
        unit("Throne Warden", 4, 2)
            .canWarp()
            .tags(Green.class)
            .desc("Endurance; gain HP equal to number of attacking Units")
            .addOnEnterEffects(endurance(), new AdjustHp(new TotalAttackingUnits())),
        unit("Stoneshaker", 4, 1)
            .tags(Red.class)
            .desc("Add 2 Power; Berserk; add 2 Strength to generated 7 or more Power")
            .addOnEnterEffects(power(2), berserk())
            .addOnConditionEffects(strength(2).addTriggerConditions(new MinPower(7))),
        unit("Temple Scribe", 1, 1)
            .tags(Yellow.class)
            .desc("Add 1 Power; draw 1 card if you played any Spell")
            .addOnEnterEffects(power(1))
            .addOnConditionEffects(drawAction(1).addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Mystic Ascendant", 6, 4)
            .tags(Yellow.class)
            .desc("Draw 1 card; add 2 Strength to this if you generated 7 or more Power")
            .addOnEnterEffects(drawAction(1))
            .addOnConditionEffects(strength(2).addTriggerConditions(new MinPower(7))),
        unit("Splimespitter Slug", 5, 3)
            .tags(Green.class, Black.class)
            .desc("Add 1 Power; give all attacking Units -1 Strength")
            .addOnEnterEffects(power(1), strength(-1).setTargetSource(new AttackingUnits())),
        unit("Impending Doom", 4, 5)
            .tags(Black.class)
            .desc("Flying; minus 1 HP")
            .addOnEnterEffects(flying(), hp(-1)),
        unit("Ridgeline Watcher", 4, 3)
            .tags(Blue.class)
            .desc("Add 2 Defense; Voidbind")
            .addOnEnterEffects(defense(2), voidbindAction()),
        unit("Pokpok, Rockpacker", 3, 1)
            .tags(Red.class, Blue.class)
            .desc("Draw 1 card; give a Unit -1 Strength")
            .addOnEnterEffects(drawAction(1), debuffAction(1)),
        unit("Snowrager", 2, 1)
            .canWarp()
            .tags(Blue.class)
            .desc("Berserk; add 1 Strength to this if you have 3 or more Units")
            .addOnEnterEffects(berserk())
            .addOnConditionEffects(strength(1).addTriggerConditions(new MinDefendingUnitCount(3))),
        unit("Cabal Spymaster", 3, 1)
            .tags(Black.class)
            .desc("Unblockable; give all defending Units with 3 or less Strength Unblockable")
            .addOnEnterEffects(
                unblockable(),
                action(
                        "Give Unblockable",
                        unblockable()
                            .setTargetSource(new DefendingUnits().addFilters(new MaxStrength(3))))
                    .desc("Give all defending Units with 3 or less Strength Unblockable")),
        unit("Oni Ronin", 1, 1)
            .canWarp()
            .tags(Red.class)
            .desc("Add 1 Strength to two Units")
            .addOnEnterEffects(
                action(
                        "+1 Strength",
                        strength(1).setInputCount(1, 2).setTargetSource(new AllUnits()))
                    .desc("Add 1 Strength to two Units")),
        unit("Treasury Guard", 4, 2)
            .tags(Green.class)
            .desc("Add 2 Power; Green: +1 Defense and Endurance")
            .addOnEnterEffects(power(2))
            .addOnConditionEffects(
                defense(1).addTriggerConditions(new PlayedTag(Green.class)),
                endurance().addTriggerConditions(new PlayedTag(Green.class))),
        unit("Iceberg Warchief", 3, 3)
            .tags(Red.class, Blue.class)
            .desc("Red: create a 2 Strength Yeti; Blue: create a 2 Strength Yeti")
            .addOnConditionEffects(
                createYeti().addTriggerConditions(new PlayedTag(Red.class)),
                createYeti().addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Lumbering Gruan", 3, 3)
            .tags(Blue.class)
            .desc("Berserk; Blue: Add 2 Power")
            .addOnEnterEffects(berserk())
            .addOnConditionEffects(power(2).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Midnight Gale", 4, 3)
            .tags(Blue.class, Black.class)
            .desc("Add 1 Power; Blue: Flying; Black: Lifesteal")
            .addOnEnterEffects(power(1))
            .addOnConditionEffects(
                flying().addTriggerConditions(new PlayedTag(Blue.class)),
                lifesteal().addTriggerConditions(new PlayedTag(Black.class))),
        unit("Cabal Cutthroat", 4, 2)
            .tags(Black.class)
            .desc("Add 2 Power; Lifesteal; Throne: Banish a card in the Forge Row")
            .addOnEnterEffects(power(2), lifesteal())
            .addOnConditionEffects(
                banishInForgeRowAction().addTriggerConditions(new ThroneActive())),
        unit("Vampire Bat", 2, 1)
            .tags(Black.class)
            .desc("Add 1 Power; Flying; Lifesteal; Black: +1 Strength")
            .addOnEnterEffects(power(1), flying(), lifesteal())
            .addOnConditionEffects(strength(1).addTriggerConditions(new PlayedTag(Black.class))),
        unit("Back-Alley Bouncer", 5, 3)
            .tags(Black.class)
            .desc("Voidbind; Black: Destroy an Unit")
            .addOnEnterEffects(voidbindAction())
            .addOnConditionEffects(
                destroyUnitAction().addTriggerConditions(new PlayedTag(Black.class))),
        unit("Blistersting Wasp", 2, 2)
            .canWarp()
            .tags(Yellow.class, Black.class)
            .desc("Flying; Yellow: Endurance; Black: +5 Defense")
            .addOnEnterEffects(flying())
            .addOnConditionEffects(
                endurance().addTriggerConditions(new PlayedTag(Yellow.class)),
                defense(5).addTriggerConditions(new PlayedTag(Black.class))),
        unit("Dune Phantom", 2, 1)
            .canWarp()
            .tags(Yellow.class)
            .desc("+5 Defense; Throne: Flying")
            .addOnEnterEffects(defense(5))
            .addOnConditionEffects(flying().addTriggerConditions(new ThroneActive())),
        unit("Steelbound Dragon", 6, 3)
            .tags(Red.class)
            .desc("Add 2 Power; Flying; Throne: +3 Strength")
            .addOnEnterEffects(power(2), flying())
            .addOnConditionEffects(strength(3).addTriggerConditions(new ThroneActive())),
        unit("Heart of the Vault", 6, 5)
            .tags(Red.class, Yellow.class)
            .desc("Red: add 2 Power; Yellow: draw a card")
            .addOnConditionEffects(
                power(2).addTriggerConditions(new PlayedTag(Red.class)),
                drawAction(1).addTriggerConditions(new PlayedTag(Yellow.class))),
        unit("Eilyn, Queen of the Wilds", 6, 4)
            .canWarp()
            .tags(Blue.class)
            .desc("Flying; Give all defending units Flying")
            .addOnEnterEffects(
                flying(),
                action("Give Flying", flying().setTargetSource(new DefendingUnits()))
                    .desc("Give Flying to all defending units")),
        unit("Corrupted Umbren", 3, 3)
            .canWarp()
            .tags(Black.class)
            .desc("+2 Defense; Lifesteal")
            .addOnEnterEffects(defense(2), lifesteal()),
        unitNoBlock("Horned Vorlunk", 2, 4).canWarp().tags(Yellow.class).desc("Can't Block"),
        unit("Icicle Marksman", 3, 2)
            .canWarp()
            .tags(Blue.class)
            .desc("Deals 3 damage if you played a spell")
            .addOnConditionEffects(
                dealDamageAction(3).addTriggerConditions(new PlayedTag(Spell.class))),
        unit("Explorer Emeritus", 4, 1)
            .tags(Yellow.class, Blue.class)
            .desc("Add 1 Power; Yellow: draw a card; Blue: draw a card")
            .addOnConditionEffects(
                drawAction(1).addTriggerConditions(new PlayedTag(Yellow.class)),
                drawAction(1).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("East-Wind Herald", 2, 1)
            .tags(Blue.class)
            .desc("Flying; +3 Defense; Blue: draw a card")
            .addOnEnterEffects(flying(), defense(3))
            .addOnConditionEffects(drawAction(1).addTriggerConditions(new PlayedTag(Blue.class))),
        unit("Rolant, the Iron Fist", 7, 4)
            .canWarp()
            .tags(Green.class)
            .desc("Endurance; Give Endurance to all defending units")
            .addOnEnterEffects(
                endurance(),
                action("Give Endurance", endurance().setTargetSource(new DefendingUnits()))
                    .desc("Give Endurance to all defending units")),
        unit("Vara, Vengeance-Seeker", 7, 4)
            .canWarp()
            .tags(Black.class)
            .desc("Lifesteal; Unblockable")
            .addOnEnterEffects(lifesteal(), unblockable()),
        unit("Sandstorm Titan", 5, 5)
            .canWarp()
            .tags(Yellow.class)
            .desc("Remove Flying from all units; Yellow: Endurance")
            .addOnEnterEffects(
                action("Remove Flying", SetFlying.removeFlying().setTargetSource(new AllUnits()))
                    .desc("Remove Flying from all units"))
            .addOnConditionEffects(endurance().addTriggerConditions(new PlayedTag(Yellow.class))),
        unit("Renegade Valkyrie", 1, 1)
            .tags(Red.class, Green.class)
            .desc("Flying; Berserk; Red: +1 Strength; Green: +1 Strength")
            .addOnEnterEffects(flying(), berserk())
            .addOnConditionEffects(
                strength(1).addTriggerConditions(new PlayedTag(Red.class)),
                strength(1).addTriggerConditions(new PlayedTag(Green.class))),
        unit("Champion of Progress", 5, 4)
            .tags(Yellow.class, Green.class)
            .desc("Add 1 Power; Yellow: Endurance; Green: +1 Strength")
            .addOnEnterEffects(power(1))
            .addOnConditionEffects(
                endurance().addTriggerConditions(new PlayedTag(Yellow.class)),
                strength(1).addTriggerConditions(new PlayedTag(Green.class))),
        unitNoBlock("Xenan Destroyer", 3, 3)
            .tags(Black.class)
            .desc("Add 1 Power; Lifesteal; Can't Block; Throne: +1 Strength")
            .addOnEnterEffects(power(1), lifesteal())
            .addOnConditionEffects(strength(1).addTriggerConditions(new ThroneActive())),
        unit("Icaria, the Liberator", 6, 3)
            .canWarp()
            .tags(Red.class, Green.class)
            .desc("Flying; Endurance; Throne: give a Unit +2 Strength")
            .addOnEnterEffects(flying(), endurance())
            .addOnConditionEffects(buffAction(2).addTriggerConditions(new ThroneActive())),
        unit("Shelterwing Rider", 3, 3)
            .tags(Green.class, Blue.class)
            .desc(
                "Flying; Blue: give a Unit Flying; Green: give all defending Flying units +1 Strength")
            .addOnEnterEffects(flying())
            .addOnConditionEffects(
                action("Give Flying", flying().setInputCount(1).setTargetSource(new AllUnits()))
                    .addTriggerConditions(new PlayedTag(Blue.class)),
                action(
                        "+1 Strength",
                        strength(1)
                            .setTargetSource(
                                new DefendingUnits()
                                    .addFilters(new UnitFilter(unit -> unit.flying))))
                    .desc("Give all defending Flying units +1 Strength")
                    .addTriggerConditions(new PlayedTag(Green.class))),
        spell("Snowball", 1)
            .canWarp()
            .tags(Blue.class)
            .desc("Add 1 Power; Deal 1 damage; Throne: create a 2 Strength Yeti")
            .addOnEnterEffects(power(1), dealDamageAction(1))
            .addOnConditionEffects(createYeti().addTriggerConditions(new ThroneActive())),
        spell("Oasis Sanctuary", 4)
            .canWarp()
            .tags(Yellow.class)
            .desc("Gain 4 HP")
            .addOnEnterEffects(hp(4)),
        spell("Brilliant Discovery", 5)
            .tags(Red.class, Yellow.class)
            .desc("Add 3 Power; Throne: banish a card in the Forge Row")
            .addOnEnterEffects(power(3))
            .addOnConditionEffects(
                banishInForgeRowAction().addTriggerConditions(new ThroneActive())),
        spell("Unfinished Business", 4)
            .tags(Green.class, Black.class)
            .desc("Add 2 Power; Green: give a Unit +2 Strength; Black: give a Unit -2 Strength")
            .addOnEnterEffects(power(2))
            .addOnConditionEffects(
                buffAction(2).addTriggerConditions(new PlayedTag(Green.class)),
                debuffAction(2).addTriggerConditions(new PlayedTag(Black.class))),
        spell("Assembly Line", 3)
            .tags(Red.class)
            .desc("Add 2 Power; Red: create two 1 Strength Grenadin")
            .addOnEnterEffects(power(2))
            .addOnConditionEffects(
                createGrenadin().addTriggerConditions(new PlayedTag(Red.class)),
                createGrenadin().addTriggerConditions(new PlayedTag(Red.class))),
        spell("Scouting Party", 5)
            .canWarp()
            .tags(Blue.class)
            .desc("Create two 2 Strength Yeti; Throne: create another 2 Strength Yeti")
            .addOnEnterEffects(createYeti(), createYeti())
            .addOnConditionEffects(createYeti().addTriggerConditions(new ThroneActive())),
        spell("Xenan Augury", 2)
            .tags(Black.class)
            .desc("Voidbind; Throne: gain 2 HP")
            .addOnEnterEffects(voidbindAction())
            .addOnConditionEffects(hp(2).addTriggerConditions(new ThroneActive())),
        spell("Read the Stars", 2)
            .tags(Green.class, Blue.class)
            .desc("Draw 1 card; Voidbind if you have a Flying Unit")
            .addOnEnterEffects(drawAction(1))
            .addOnConditionEffects(
                voidbindAction().addTriggerConditions(new AnyDefendingUnit(unit -> unit.flying))),
        spell("Levitate", 2)
            .tags(Blue.class)
            .desc("Add 2 Power; give a Unit Flying")
            .addOnEnterEffects(
                power(2),
                action("Give Flying", flying().setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit Flying")),
        spell("Arcane Defense", 3)
            .tags(Green.class)
            .desc("Draw 1 card; give two Units -1 Strength")
            .addOnEnterEffects(
                drawAction(1),
                action(
                        "-1 Strength",
                        strength(-1).setInputCount(1, 2).setTargetSource(new AllUnits()))
                    .desc("Give two Units -1 Strength")),
        spell("Lightning Storm", 2)
            .tags(Blue.class)
            .desc("Voidbind; destroy all attacking Units with 2 or less Strength")
            .addOnEnterEffects(
                voidbindAction(),
                action(
                        "Destroy Units",
                        destroyUnit()
                            .setTargetSource(new AttackingUnits().addFilters(new MaxStrength(2))))
                    .desc("Destroy all attacking Units with 2 or less Strength")),
        spell("Bolster", 3)
            .tags(Yellow.class)
            .desc("Add 2 Power; give a Unit +3 Defense")
            .addOnEnterEffects(
                power(2),
                action("+3 Defense", defense(3).setInputCount(1).setTargetSource(new AllUnits()))
                    .desc("Give a Unit +3 Defense")),
        spell("Hipshot", 3)
            .tags(Red.class)
            .desc("Add 1 Power; deal 2 damage to a Player")
            .addOnEnterEffects(power(1), dealDamageAction(2)),
        spell("Wisdom of the Elders", 5)
            .tags(Blue.class)
            .desc("Draw 2 cards")
            .addOnEnterEffects(drawAction(2)),
        spell("Pack Hunt", 4)
            .tags(Yellow.class)
            .desc(
                "Add 2 Power; give all defending Units +2 Strength if you generated at least 7 Power")
            .addOnEnterEffects(power(2))
            .addOnConditionEffects(
                action("+2 Strength", strength(2).setTargetSource(new DefendingUnits()))
                    .desc("Give all defending Units +2 Strength")
                    .addTriggerConditions(new MinPower(7))),
        spell("Deathstrike", 3)
            .tags(Black.class)
            .desc("Add 1 Power; destroy a Unit")
            .addOnEnterEffects(power(1), destroyUnitAction()));
  }

  public static CreateEntity drawAction(int count) {
    String str = String.format("Draw %d", count);
    return action(str, draw(count)).desc(str + " cards");
  }

  public static CreateEntity voidbindAction() {
    return action(
            "Voidbind", new BanishCard().setInputCount(1).setTargetSource(new VoidbindableCards()))
        .desc("Banish a card from your Hand or Discard Pile");
  }

  public static CreateEntity destroyUnitAction() {
    return action("Destroy Unit", destroyUnit().setInputCount(1).setTargetSource(new AllUnits()))
        .desc("Destroy a Unit");
  }

  public static CreateEntity dealDamageAction(int hp) {
    String str = String.format("Deal %d damage", hp);
    return action(str, hp(-hp).setInputCount(1).setTargetSource(new ActivePlayers()))
        .desc(str + " to a Player");
  }

  public static CreateEntity banishInForgeRowAction() {
    return action("Banish", new BanishCard().setInputCount(1).setTargetSource(new ForgeRowCards()))
        .desc("Banish a card from the Forge Row");
  }

  public static CreateEntity buffAction(int strength) {
    String str = String.format("+%d Strength", strength);
    return action(str, strength(strength).setInputCount(1).setTargetSource(new AllUnits()))
        .desc("Give a Unit " + str);
  }

  public static CreateEntity debuffAction(int strength) {
    String str = String.format("-%d Strength", strength);
    return action(str, strength(-strength).setInputCount(1).setTargetSource(new AllUnits()))
        .desc("Give a Unit " + str);
  }

  public static CreateEntity createYeti() {
    return createUnitToken("Yeti", 2);
  }

  public static CreateEntity createGrenadin() {
    return createUnitToken("Grenadin", 1);
  }
}

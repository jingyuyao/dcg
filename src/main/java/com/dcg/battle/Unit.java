package com.dcg.battle;

import com.artemis.Component;

/**
 * Primary tag for a "unit entity". Unit entities are generated from card effects to participate in
 * battle. This allows status modifiers to target units rather than cards themselves so they can be
 * easily cleaned up after each battle. Units are owned by a player.
 */
public class Unit extends Component {
  public int strength;
  public int defense;
  public boolean flying;
  public boolean lifeSteal;
  public boolean berserk;
  public boolean endurance;
  public boolean unblockable;
}

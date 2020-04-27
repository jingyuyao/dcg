package com.dcg.battle;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/**
 * Primary tag for a "unit entity". Unit entities are generated from card effects to participate in
 * battle. This allows status modifiers to target units rather than cards themselves so they can be
 * easily cleaned up after each battle. Units are owned by a player.
 */
public class Unit extends Component {
  @EntityId public int cardEntity = -1;
  public int strength = 0;
  public int defense = 0;
  public boolean flying = false;
  public boolean lifeSteal = false;
  public boolean berserk = false;
  public boolean endurance = false;
  public boolean unblockable = false;
}

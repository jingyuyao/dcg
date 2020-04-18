package com.dcg.battle;

import com.artemis.Component;

/**
 * Primary tag for a "unit entity". Unit entities are generated from card effects to participate in
 * battle. This allows status modifiers to target units rather than cards themselves so they can be
 * easily cleaned up after each battle. Units are owned by a player.
 */
public class Unit extends Component {
  public int strength = 0;
  public int defense = 0;
  public boolean flying = false;
  public boolean lifeSteal = false;
  public boolean berserk = false;
  public boolean endurance = false;
  public boolean unblockable = false;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (strength > 0) {
      builder.append("atk️️=").append(strength).append(" ");
    }
    if (defense > 0) {
      builder.append("def=").append(defense).append(" ");
    }
    if (flying) {
      builder.append("flying").append(" ");
    }
    if (lifeSteal) {
      builder.append("lifeSteal").append(" ");
    }
    if (berserk) {
      builder.append("berserk").append(" ");
    }
    if (endurance) {
      builder.append("endurance").append(" ");
    }
    if (unblockable) {
      builder.append("unblockable").append(" ");
    }
    return builder.toString();
  }
}

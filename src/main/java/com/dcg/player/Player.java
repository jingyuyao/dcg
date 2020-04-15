package com.dcg.player;

import com.artemis.Component;

/** Primary tag for a "player entity". */
public class Player extends Component {
  public String name;
  public int hp = 25;

  @Override
  public String toString() {
    return String.format("%s(hp:%d)", name, hp);
  }
}

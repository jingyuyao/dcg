package com.dcg.player;

import com.artemis.Component;

/** Primary tag for a "player entity". */
public class Player extends Component {
  public int hp = 25;

  @Override
  public String toString() {
    return String.format("hp=%d", hp);
  }
}

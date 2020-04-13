package com.dcg.player;

import com.artemis.Component;

public class Player extends Component {
  public String name;
  public int hp = 15;
  // TODO: split this out of player since its only for the current player
  public int powerPool = 0;

  @Override
  public String toString() {
    return String.format("%s(hp:%d)", name, hp);
  }
}

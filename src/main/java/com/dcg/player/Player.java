package com.dcg.player;

import com.artemis.Component;

public class Player extends Component {
  public String name;
  public int hp = 15;
  public int power = 0;

  @Override
  public String toString() {
    return String.format("%s(hp:%d,power:%d)", name, hp, power);
  }
}

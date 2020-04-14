package com.dcg.battle;

import com.artemis.Component;

public class Unit extends Component {
  public String name = "Token";
  public int strength = 0;

  @Override
  public String toString() {
    return String.format("%s(str:%d)", name, strength);
  }
}

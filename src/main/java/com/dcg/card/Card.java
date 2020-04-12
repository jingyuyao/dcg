package com.dcg.card;

import com.artemis.Component;

public class Card extends Component {
  public String name = "dummy";
  public int cost = 0;
  public int power = 0;

  @Override
  public String toString() {
    return String.format("%s(cost:%d,power:%d)", name, cost, power);
  }
}

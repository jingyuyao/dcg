package com.dcg.card;

import com.artemis.Component;

/**
 * Primary tag for a "card entity". A card is a long lived entity that may or may not be owned by a
 * player.
 */
public class Card extends Component {
  public String name = "Dummy";
  public int cost = 0;

  @Override
  public String toString() {
    return String.format("%s(%d)", name, cost);
  }
}

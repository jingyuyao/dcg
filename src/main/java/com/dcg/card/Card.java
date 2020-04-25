package com.dcg.card;

import com.artemis.Component;

/**
 * Primary tag for a "card entity". A card is a long lived entity that may or may not be owned by a
 * player.
 */
public class Card extends Component {
  public int cost = 0;
}

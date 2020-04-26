package com.dcg.player;

import com.artemis.Component;

/**
 * Primary tag for a "player entity". Player entities are persisted throughout the lifetime of the
 * game.
 */
public class Player extends Component {
  public int hp = 25;
  public int flashTokens = 2;
}

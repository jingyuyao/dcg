package com.dcg.card;

import com.artemis.Component;

public class Card extends Component {

  public String name;
  public Location location = Location.NONE;

  public enum Location {
    NONE,
    DRAW_PILE,
    DISCARD_PILE,
    HAND,
  }
}

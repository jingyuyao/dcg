package com.dcg.location;

import com.artemis.Component;
import java.util.Arrays;
import java.util.List;

public class Location extends Component {
  public static final List<Class<? extends Location>> ALL =
      Arrays.asList(
          Deck.class,
          ForgeRow.class,
          DiscardPile.class,
          Hand.class,
          PlayArea.class,
          BattleArea.class);
}

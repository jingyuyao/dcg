package com.dcg.card;

import com.artemis.Component;
import java.util.Arrays;
import java.util.List;

public class Card extends Component {

  public static final List<Class<? extends Location>> LOCATIONS =
      Arrays.asList(ForgeRow.class, Deck.class, DiscardPile.class, PlayArea.class);

  public String name;
}

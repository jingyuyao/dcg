package com.dcg.player;

import com.artemis.Component;
import com.dcg.deck.Card;
import com.dcg.deck.Deck;
import java.util.ArrayList;
import java.util.List;

public class Player extends Component {

  public final Deck deck = new Deck();
  public final List<Card> hand = new ArrayList<>();
  public int health = 25;
  public String name;

  @Override
  public String toString() {
    return "Player{" + "name='" + name + '\'' + ", health=" + health + ", hand=" + hand + '}';
  }
}

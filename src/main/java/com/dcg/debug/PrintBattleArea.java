package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.BattleArea;
import com.dcg.ownership.Owned;
import com.dcg.turn.TurnSystem;
import com.dcg.util.AspectSystem;
import java.util.ArrayList;
import java.util.List;

public class PrintBattleArea extends Command {
  protected TurnSystem turnSystem;
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Strength> mStrength;

  @Override
  public void run() {
    List<Integer> attacking = new ArrayList<>();
    List<Integer> defending = new ArrayList<>();
    for (int entity : aspectSystem.get(Aspect.all(BattleArea.class, Owned.class, Strength.class))) {
      if (turnSystem.getCurrentPlayerEntity() == mOwned.get(entity).owner) {
        defending.add(entity);
      } else {
        attacking.add(entity);
      }
    }
    System.out.println("    Attacking");
    for (int entity : attacking) {
      System.out.printf("      *%d %s str: %s\n", entity, mCard.get(entity), mStrength.get(entity));
    }
    System.out.println("    Defending");
    for (int entity : defending) {
      System.out.printf("      *%d %s str: %s\n", entity, mCard.get(entity), mStrength.get(entity));
    }
  }
}

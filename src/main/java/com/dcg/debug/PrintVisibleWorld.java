package com.dcg.debug;

import static com.dcg.game.CoreSystem.toIntBag;

import com.artemis.Aspect;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.utils.IntBag;
import com.dcg.action.Action;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.ForgeRow;
import com.dcg.player.Player;
import com.dcg.target.Target;
import java.io.ByteArrayOutputStream;

public class PrintVisibleWorld extends AbstractCommandBuilder {
  protected WorldSerializationManager serializationManager;

  @Override
  protected void run(Target target) {
    IntBag forgeRow =
        coreSystem.getStream(Aspect.all(Card.class, ForgeRow.class)).boxed().collect(toIntBag());
    IntBag attackingUnits = coreSystem.getAttackingEntities().boxed().collect(toIntBag());
    IntBag defendingUnits = coreSystem.getDefendingEntities().boxed().collect(toIntBag());
    IntBag players = coreSystem.getStream(Aspect.all(Player.class)).boxed().collect(toIntBag());
    IntBag actions = coreSystem.getStream(Aspect.all(Action.class)).boxed().collect(toIntBag());
    printJson("Forge Row", forgeRow);
    printJson("Attacking Units", attackingUnits);
    printJson("Defending Units", defendingUnits);
    printJson("Players", players);
    printJson("Actions", actions);
  }

  protected void printJson(String name, IntBag entities) {
    System.out.printf("------------------- %s -------------------\n", name);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    serializationManager.save(outputStream, new SaveFileFormat(entities));
    System.out.println(outputStream.toString());
  }
}

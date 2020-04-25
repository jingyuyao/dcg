package com.dcg.card;

import com.artemis.Component;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import java.util.List;

public class MercenaryExit extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    int cardEntity = coreSystem.getParent(originEntity);
    for (Class<? extends Component> color : Colors.ALL) {
      world.getMapper(color).remove(cardEntity);
    }
  }
}

package com.dcg.card;

import com.artemis.Component;
import com.dcg.action.CreateAction;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import java.util.List;

public class MercenaryEnter extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (Class<? extends Component> color : Colors.ALL) {
      commandChain.addEnd(
          new CreateAction(new Mercenary(color))
              .desc("Be " + color.getSimpleName())
              .build(world, originEntity));
    }
  }
}

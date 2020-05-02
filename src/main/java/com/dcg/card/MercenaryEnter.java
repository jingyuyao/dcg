package com.dcg.card;

import static com.dcg.action.CreateAction.action;

import com.artemis.Component;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import java.util.List;

public class MercenaryEnter extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (Class<? extends Component> color : Colors.ALL) {
      commandChain.addEnd(
          action(color.getSimpleName(), new Mercenary(color))
              .desc("Become " + color.getSimpleName())
              .build(world, originEntity));
    }
  }
}

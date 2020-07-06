package com.dcg.card;

import static com.dcg.action.CreateAction.action;

import com.artemis.Component;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;

public class MercenaryEnter extends AbstractCommandBuilder {
  @Override
  protected void run(CommandData data) {
    for (Class<? extends Component> color : Colors.ALL) {
      commandChain.addEnd(
          action(color.getSimpleName(), new Mercenary(color))
              .desc("Become " + color.getSimpleName())
              .build(world, data.getOriginEntity()));
    }
  }
}

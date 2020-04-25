package com.dcg.card;

import com.artemis.Component;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import java.util.List;

public class Mercenary extends AbstractCommandBuilder {
  private final Class<? extends Component> color;

  public Mercenary(Class<? extends Component> color) {
    this.color = color;
    addTriggerConditions(
        (originEntity, allowedTargets) -> {
          int cardEntity = coreSystem.getParent(originEntity);
          for (Class<? extends Component> clazz : Colors.ALL) {
            if (world.getMapper(clazz).has(cardEntity)) {
              return false;
            }
          }
          return true;
        });
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    world.getMapper(color).create(coreSystem.getParent(originEntity));
  }
}

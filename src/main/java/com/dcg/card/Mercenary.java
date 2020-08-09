package com.dcg.card;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;

public class Mercenary extends AbstractCommandBuilder {
  private final Class<? extends Component> color;
  protected ComponentMapper<Unit> mUnit;

  public Mercenary(Class<? extends Component> color) {
    this.color = color;
    addTriggerConditions(
        (originEntity, allowedTargets) -> {
          Unit unit = mUnit.get(originEntity);
          for (Class<? extends Component> clazz : Colors.ALL) {
            if (world.getMapper(clazz).has(unit.cardEntity)) {
              return false;
            }
          }
          return true;
        });
  }

  @Override
  protected void run(CommandData data) {
    Unit unit = mUnit.get(data.getOriginEntity());
    world.getMapper(color).create(unit.cardEntity);
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format("becomes %s", color.getSimpleName());
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    return true;
  }
}

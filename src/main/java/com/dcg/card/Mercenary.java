package com.dcg.card;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import java.util.List;

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
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    Unit unit = mUnit.get(originEntity);
    world.getMapper(color).create(unit.cardEntity);
  }

  @Override
  public String toString() {
    return String.format("%s to %s", super.toString(), color.getSimpleName());
  }
}

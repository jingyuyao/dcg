package com.dcg.card;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;

public class MercenaryExit extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run(CommandData data) {
    Unit unit = mUnit.get(data.getOriginEntity());
    for (Class<? extends Component> color : Colors.ALL) {
      world.getMapper(color).remove(unit.cardEntity);
    }
  }
}

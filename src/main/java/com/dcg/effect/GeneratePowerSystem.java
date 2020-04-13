package com.dcg.effect;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.command.CommandChain;
import com.dcg.location.PlayArea;
import com.dcg.player.AdjustPower;

@All({GeneratePower.class, PlayArea.class})
public class GeneratePowerSystem extends BaseEntitySystem {
  @Wire protected CommandChain commandChain;
  protected ComponentMapper<GeneratePower> mAddPower;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    GeneratePower generatePower = mAddPower.get(entityId);
    if (generatePower.power > 0) {
      commandChain.addStart(new AdjustPower(generatePower.power));
    }
  }

  @Override
  protected void processSystem() {}
}

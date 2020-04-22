package com.dcg.target;

import com.artemis.Aspect;
import com.dcg.battle.Unit;
import java.util.List;
import java.util.stream.Collectors;

public class UnitInputs extends Inputs {
  public UnitInputs() {}

  public UnitInputs(int maxInputs) {
    super(maxInputs);
  }

  public UnitInputs(int minInputs, int maxInputs) {
    super(minInputs, maxInputs);
  }

  @Override
  public List<Integer> getAllowedInputs() {
    return coreSystem.getStream(Aspect.all(Unit.class)).collect(Collectors.toList());
  }
}

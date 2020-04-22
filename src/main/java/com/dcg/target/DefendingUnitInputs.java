package com.dcg.target;

import java.util.List;
import java.util.stream.Collectors;

public class DefendingUnitInputs extends Inputs {
  public DefendingUnitInputs() {}

  public DefendingUnitInputs(int maxInputs) {
    super(maxInputs);
  }

  public DefendingUnitInputs(int minInputs, int maxInputs) {
    super(minInputs, maxInputs);
  }

  @Override
  public List<Integer> getAllowedInputs() {
    return coreSystem.getDefendingEntities().boxed().collect(Collectors.toList());
  }
}

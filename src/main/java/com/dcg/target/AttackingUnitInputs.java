package com.dcg.target;

import java.util.List;
import java.util.stream.Collectors;

public class AttackingUnitInputs extends Inputs {
  public AttackingUnitInputs() {}

  public AttackingUnitInputs(int maxInputs) {
    super(maxInputs);
  }

  public AttackingUnitInputs(int minInputs, int maxInputs) {
    super(minInputs, maxInputs);
  }

  @Override
  public List<Integer> getAllowedTargets() {
    return coreSystem.getAttackingEntities().collect(Collectors.toList());
  }
}

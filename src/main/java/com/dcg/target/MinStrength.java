package com.dcg.target;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import java.util.stream.Stream;

public class MinStrength implements TargetFilter {
  private final int strength;
  protected ComponentMapper<Unit> mUnit;

  public MinStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public Stream<Integer> apply(Integer originEntity, Stream<Integer> source) {
    return source.filter(unitEntity -> mUnit.get(unitEntity).strength >= strength);
  }
}

package com.dcg.target;

import com.dcg.command.Input;
import java.util.Collections;
import java.util.List;

// TODO: rename this or Input
public class Inputs implements TargetFunction {
  @Override
  public Target apply(Integer originEntity, Input input) {
    return new Target() {
      @Override
      public int getOrigin() {
        return originEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return input.get().isPresent()
            ? Collections.singletonList(input.get().getAsInt())
            : Collections.emptyList();
      }
    };
  }
}

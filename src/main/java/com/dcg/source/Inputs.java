package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.Collections;
import java.util.List;

// TODO: rename this or Input
public class Inputs implements TargetFunction {
  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return new Target() {
      @Override
      public int getFrom() {
        return sourceEntity;
      }

      @Override
      public List<Integer> getTo() {
        return input.get().isPresent()
            ? Collections.singletonList(input.get().getAsInt())
            : Collections.emptyList();
      }
    };
  }
}

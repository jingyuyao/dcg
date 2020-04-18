package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.ArrayList;
import java.util.List;

public class SourceEntityAndInputs implements CommandSource {
  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () -> {
      List<Integer> result = new ArrayList<>(input.get().size() + 1);
      result.add(sourceEntity);
      result.addAll(input.get());
      return result;
    };
  }
}

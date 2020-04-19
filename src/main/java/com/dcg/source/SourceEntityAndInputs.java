package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.ArrayList;
import java.util.List;

public class SourceEntityAndInputs implements CommandSource {
  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () -> {
      List<Integer> result = new ArrayList<>(2);
      result.add(sourceEntity);
      input.get().ifPresent(result::add);
      return result;
    };
  }
}

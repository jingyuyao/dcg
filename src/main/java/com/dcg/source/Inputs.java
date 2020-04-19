package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.Collections;
import java.util.OptionalInt;

public class Inputs implements CommandSource {
  @Override
  public Target apply(Integer sourceEntity, Input input) {
    OptionalInt value = input.get();
    if (value.isPresent()) {
      return () -> Collections.singletonList(value.getAsInt());
    } else {
      return Collections::emptyList;
    }
  }
}

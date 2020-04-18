package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;

public class Inputs implements CommandSource {
  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return input::get;
  }
}

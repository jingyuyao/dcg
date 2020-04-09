package com.dcg;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

public class Input {

  @Parameter
  public List<String> parameters = new ArrayList<>();

  @Parameter(names = "-quit")
  public boolean quit = false;

  @Parameter(names = "-print")
  public boolean print = false;

  // TODO: make this take a list
  @Parameter(names = "-choose")
  public Integer choose = -1;
}

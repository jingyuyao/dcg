package com.dcg.game;

import com.artemis.Component;

/** All entities should have this. */
public class Common extends Component {
  public String name = "No name";
  public String description = "";

  @Override
  public String toString() {
    return name;
  }
}

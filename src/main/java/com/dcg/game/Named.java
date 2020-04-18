package com.dcg.game;

import com.artemis.Component;

/** All entities should have this. */
public class Named extends Component {
  public String name = "No name";

  @Override
  public String toString() {
    return name;
  }
}

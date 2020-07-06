package com.dcg.api.legacy;

import com.dcg.game.Common;

public class EntityView {
  public final int id;
  public final String name;
  public final String description;

  public EntityView(int id, Common common) {
    this.id = id;
    this.name = common.name;
    this.description = common.description;
  }
}

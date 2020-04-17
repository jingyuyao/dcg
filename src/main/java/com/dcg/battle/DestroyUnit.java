package com.dcg.battle;

public class DestroyUnit extends AdjustUnit {
  @Override
  protected void run() {
    world.delete(getTargetEntity());
  }
}

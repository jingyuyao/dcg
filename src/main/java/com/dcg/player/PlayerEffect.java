package com.dcg.player;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.targetsource.OriginEntityRoot;

abstract class PlayerEffect extends AbstractCommandBuilder {
  PlayerEffect() {
    setTargetSource(new OriginEntityRoot());
  }
}

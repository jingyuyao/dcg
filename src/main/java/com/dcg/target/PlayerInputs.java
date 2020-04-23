package com.dcg.target;

import com.artemis.Aspect;
import com.dcg.player.Player;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerInputs extends Inputs {
  public PlayerInputs() {}

  public PlayerInputs(int maxInputs) {
    super(maxInputs);
  }

  public PlayerInputs(int minInputs, int maxInputs) {
    super(minInputs, maxInputs);
  }

  @Override
  public List<Integer> getAllowedTargets() {
    return coreSystem.getStream(Aspect.all(Player.class)).collect(Collectors.toList());
  }
}

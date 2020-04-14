package com.dcg.effect;

import com.artemis.Component;
import com.dcg.command.Command;
import java.util.ArrayList;
import java.util.List;

public class OnPlay extends Component {
  public List<Command> effects = new ArrayList<>(1);
}

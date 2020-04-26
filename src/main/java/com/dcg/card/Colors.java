package com.dcg.card;

import com.artemis.Component;
import java.util.Arrays;
import java.util.List;

public class Colors {
  public static final List<Class<? extends Component>> ALL =
      Arrays.asList(Red.class, Green.class, Blue.class, Yellow.class, Black.class);
}

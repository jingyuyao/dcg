package com.dcg.card;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.ownership.Own;
import java.util.ArrayList;
import java.util.List;

public class CreateCard extends Command {
  private final String name;
  private final Class<? extends Location> location;
  private final List<Class<? extends Component>> tags = new ArrayList<>();
  private int owner = -1;
  @Wire CommandChain commandChain;
  World world;
  ComponentMapper<Card> mCard;

  public CreateCard(String name, Class<? extends Location> location) {
    this.name = name;
    this.location = location;
  }

  public CreateCard setOwner(int owner) {
    this.owner = owner;
    return this;
  }

  public CreateCard addTag(Class<? extends Component> tag) {
    tags.add(tag);
    return this;
  }

  @Override
  public void run() {
    int cardEntity = world.create();
    Card card = mCard.create(cardEntity);
    card.name = name;
    for (Class<? extends Component> tag : tags) {
      world.getMapper(tag).create(cardEntity);
    }
    List<Command> commands = new ArrayList<>();
    commands.add(new MoveLocation(cardEntity, location));
    if (owner != -1) {
      commands.add(new Own(owner, cardEntity));
    }
    commandChain.addStart(commands);
  }

  @Override
  public String toString() {
    return super.toString() + name;
  }
}

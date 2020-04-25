# Entities
- All
  - Attached actions will be deleted upon leaving the effect area
  - Tree based ownership
- Player
  - Persistent throughout the game
  - Turn component cycles through them
- Card
  - Cycles through deck, hand, play area and discard pile
  - Perform effects when played
- Unit
  - Lives the in the 'battle area'
  - Destroyed and cleaned up after damage phase
- Action
  - Contains a command that can be auto executed or executed by a player

# Commands
- Has optional owner entity and input
- Invoked through a queue
- Don't use injected fields for toString()

# Serialization
### Auto references, tl;dr don't use @EntityId
@EntityId beats out transient. Any referenced entities will be
automatically picked up by the serializer. So if we serialize child then all of its parents will be
included in the output as well. We don't really need to use it since the only thing that need
to be cleaned up is actions and that's already done by the DeleteAction effect attached to
every created entity when they leave their play area.

### Components without data or component whose data didn't change
These components are not stores as a part of the "components" object. They will only be listed in
"archetypes".

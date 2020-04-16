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

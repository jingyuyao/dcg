# Entities
- Player
  - persistent throughout the game
  - turn component cycles through them
- Card
  - cycles through deck, hand, play area and discard pile
  - perform effects when played
- Unit
  - lives the in the 'battle area'
  - destroyed and cleaned up after damage phase
- Action
  - contains a command that can be auto executed or executed by a player
  - cleaned up after each turn

# Commands
- Don't use injected fields for toString()

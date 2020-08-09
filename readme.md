# Deployment

1. Package shaded jar `mvn package`
2. Copy `target/dcg-{version}-shaded.jar` to server
    1. `gcloud compute scp {PATH_TO_JAR} {GCP_INSTANCE}:`
3. Run jar `sudo nohup java -jar {JAR_NAME} prod`
 
# Entities
- All
  - Tree based ownership
  - Entities are never 'deleted', they simply have their `Active` tag removed to indicate that they
    are no longer part of the game
    - This is to support logging and referencing 'banished' entities
- Player
  - Persistent throughout the game
  - Turn component cycles through them
  - Not owned by anybody
- Card
  - Cycles through deck, hand, play area and discard pile
  - Perform effects when played
  - Owned by a player
- Unit
  - Lives the in the 'battle area'
  - Destroyed and cleaned up after damage phase
  - Owned by a player
- Action
  - Contains a command that can be auto executed or executed by a player
  - Owned by a player, card or unit

# Commands
- Has optional owner entity and input
- Invoked through a queue

### Components without data or component whose data didn't change
These components are not stores as a part of the "components" object. They will only be listed in
"archetypes".

# DCG (Digital Card Game) Server

Turn-based digital card game server built in Java using Artemis-odb (Entity Component System) and WebSockets.

---

## Running Locally

### Prerequisites

Configure your Java environment (Java 8+ / OpenJDK 17+):

```bash
export JAVA_HOME="/opt/homebrew/opt/openjdk"
export PATH="$JAVA_HOME/bin:$PATH"
```

### Build and Run

```bash
mvn exec:java -Dexec.mainClass="com.example.MainClass"
```

---

## Deployment to Fly.io

1. **Install Fly CLI** (if not installed):

   ```bash
   brew install flyctl
   ```

2. **Login & Launch**:

   ```bash
   fly auth login
   fly launch --no-deploy
   ```

   _(Select or accept `fly.toml` configuration)._

3. **Deploy**:
   ```bash
   fly deploy
   ```

Fly.io will build the multi-stage Docker container and expose secure WebSockets on `wss://<app-name>.fly.dev`.

---

## WebSocket Client API Protocol

Connect to `ws://localhost:8888` (local) or `wss://<app-name>.fly.dev` (production).

All messages are JSON objects formatted as `{ "kind": "...", ... }`.

### Client -> Server Messages (`ClientMessage`)

| Kind             | Arguments                                    | Description                                                 |
| :--------------- | :------------------------------------------- | :---------------------------------------------------------- |
| `GET_ROOM_LIST`  | _(none)_                                     | Requests current list of active rooms.                      |
| `CREATE_ROOM`    | `strArgs: [roomName, (optional) playerName]` | Dynamically creates a new room and optionally joins it.     |
| `JOIN_ROOM`      | `strArgs: [roomName, playerName]`            | Joins an existing room.                                     |
| `LEAVE_ROOM`     | _(none)_                                     | Leaves current room. Empty rooms are deleted automatically. |
| `START_GAME`     | _(none)_                                     | Starts the game in the current room (requires 2-4 players). |
| `EXECUTE_ACTION` | `intArgs: [actionId, ...targetIds]`          | Executes a chosen action.                                   |
| `GET_GAME_LOGS`  | `intArgs: [startIndex]`                      | Fetches execution logs starting from `startIndex`.          |

### Server -> Client Messages (`ServerMessage`)

| Kind              | Payload (`data`)                                                | Description                                                     |
| :---------------- | :-------------------------------------------------------------- | :-------------------------------------------------------------- |
| `ATTACHMENT_VIEW` | `{ playerName, roomName }`                                      | Sent on connection/room transition.                             |
| `ROOM_LIST`       | `{ rooms: [ GameRoomView ] }`                                   | List of active rooms and their player counts.                   |
| `GAME_ROOM_VIEW`  | `{ roomName, joinedPlayerNames, isGameInProgress, isGameOver }` | Lobby state for current room.                                   |
| `GAME_VIEW`       | `{ playerId, currentPlayerId, players, cards, units }`          | Sanitized game board state snapshot.                            |
| `GAME_LOGS`       | `{ logs: [ ... ] }`                                             | Game history logs.                                              |
| `ERROR`           | `{ message: string }`                                           | Sent when an invalid action, full room, or invalid name occurs. |

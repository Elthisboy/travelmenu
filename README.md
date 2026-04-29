# Travel Menu

## Project Identity
- **Name:** travelmenu
- **Mod ID:** `travelmenu`
- **Version:** `1.0.0` (Resolved at build time)

## Technical Summary
The **travelmenu** mod provides a GUI-driven fast-travel system tailored for custom maps. The core functionality centers around bidirectional network payloads (`OpenTravelMenuPayload` [S2C] and `TeleportRequestPayload` [C2S]). It allows players to open a client-side screen to view a list of configured destinations. Upon selection, a request is sent to the server to validate the teleportation, checking against an optional travel cost managed through an integrated economy system (`com.elthisboy.travelmenu.economy`), before finally executing the coordinate transfer.

## Feature Breakdown
- **Graphical Interface Navigation:** Renders a visual menu populated with fast-travel waypoints, utilizing custom item icons for easy identification.
- **JSON-Driven Waypoints:** All travel destinations are fully configurable via JSON, defining unique IDs, translation keys for names and lore, display icons, and exact `X`, `Y`, `Z` target coordinates.
- **Economy Integration:** Each destination can be assigned a specific travel `cost`. The server will validate the player's funds before authorizing the teleportation request.
- **Secure Network Validation:** Teleportation relies strictly on Server-Side validation via `TeleportRequestPayload` to prevent client-side coordinate spoofing or bypassing travel costs.

## Command Registry
*Note: All commands require OP Permission Level 2.*

| Command | Description | Permission Level |
| :--- | :--- | :--- |
| `/updateDestinations` | Hot-reloads the `destinations.json` file from disk, allowing dynamic waypoint updates without restarting the server. | OP (2) |

## Configuration Schema
The mod expects/generates the configuration file at `config/travelmenu/destinations.json`:

```json
{
  "destinations": [
    {
      "id": "market",
      "name": "travel.destination.market",
      "description": "travel.destination.market.desc",
      "icon": "minecraft:emerald",
      "x": 100,
      "y": 70,
      "z": -50,
      "cost": 0
    },
    {
      "id": "forest",
      "name": "travel.destination.forest",
      "description": "travel.destination.forest.desc",
      "icon": "minecraft:oak_sapling",
      "x": 200,
      "y": 70,
      "z": -80,
      "cost": 10
    }
  ]
}
```

## Developer Info
- **Author:** El_this_boy
- **Platform:** Fabric 1.21.1

# DeathAnnouncer API

Public, Bukkit-free Java API for integrating with [**DeathAnnouncer**](../README.md) on Minecraft backends (Paper, Folia, Spigot, and forks).

| | |
|---|---|
| **Artifact** | `com.github.zBrunoC:DeathAnnouncer-API:2.0.0-RELEASE` |
| **Java** | 17+ |
| **Runtime** | DeathAnnouncer plugin must be installed; `DeathLogAPI` is initialized when death logging is enabled |

The API module ships **no** server, database, Discord, or Bukkit dependencies. Integrations use plain Java types, `UUID`, and `CompletableFuture` for async death-log queries.

Published via [JitPack](https://jitpack.io/#zBrunoC/DeathAnnouncer-API) from [github.com/zBrunoC/DeathAnnouncer-API](https://github.com/zBrunoC/DeathAnnouncer-API).

---

## Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.zBrunoC</groupId>
        <artifactId>DeathAnnouncer-API</artifactId>
        <version>2.0.0-RELEASE</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## Gradle (Groovy DSL)

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.zBrunoC:DeathAnnouncer-API:2.0.0-RELEASE'
}
```

## Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.zBrunoC:DeathAnnouncer-API:2.0.0-RELEASE")
}
```

Declare **DeathAnnouncer** as a `depend` or `softdepend` in your `plugin.yml` so the provider and event bus are available at runtime.

---

## Overview

| Entry point | Purpose |
|-------------|---------|
| [`DeathAnnouncerAPI`](#event-bus) | Global `EventBus` for death, restore, killstreak, and telemetry hooks |
| [`DeathLogAPI`](#death-log-api) | Read death records, loot anti-dupe data, and PvP stats (sync + async) |

---

## Event bus

Subscribe to plugin lifecycle events without touching Bukkit listeners:

```java
import net.zbrunoc.api.DeathAnnouncerAPI;
import net.zbrunoc.api.event.PlayerDeathAnnounceEvent;

DeathAnnouncerAPI.getEventBus().subscribe(PlayerDeathAnnounceEvent.class, event -> {
    if (event.isCancelled()) return;

    // Rewrite the broadcast text (supports & legacy and MiniMessage tags)
    event.setMessage(event.getMessage() + " <gray>|</gray> <yellow>Custom addon</yellow>");
});
```

### Events

| Event | Cancellable | When it fires |
|-------|-------------|----------------|
| `PlayerDeathAnnounceEvent` | Yes | Before chat / proxy / Discord broadcast; includes `DeathLogEntry` when logging is on |
| `PlayerDeathBlackBoxEvent` | No | After telemetry capture and verdict evaluation, before broadcast |
| `PlayerRestoreApproveEvent` | Yes | Before a restore claim is queued (GUI, Discord, or API) |
| `PlayerInventoryRestoreEvent` | Yes | When items and XP are delivered to the victim |
| `PlayerKillstreakMilestoneEvent` | No | When a configured killstreak milestone is reached |

### Event details

**`PlayerDeathAnnounceEvent`**

- `getVictim()` / `getKiller()` → `IUser` (abstraction over online players)
- `getDeathLog()` → `DeathLogEntry` or `null`
- `getMessage()` / `setMessage(String)` → formatted death line (`&`, `&#RRGGBB`, MiniMessage)

**`PlayerDeathBlackBoxEvent`**

- `getDeathLog()`, `getTelemetry()`, `getVerdict()` — use for external anti-cheat, analytics, or alerting.

**`PlayerRestoreApproveEvent`**

- `getDeathId()`, `getTargetUuid()`, `getApprovedBy()`, `getSource()` (`RestoreSource`: `GUI`, `DISCORD`, `API`), `getPickedUpCount()`
- Cancel to block the restore from being created.

**`PlayerInventoryRestoreEvent`**

- `getAdmin()`, `getVictim()`, `getDeathLog()`, `getRestoredExp()`

**`PlayerKillstreakMilestoneEvent`**

- `getUser()`, `getStreak()`

---

## Death Log API

Static facade backed by `DeathLogProvider` (registered by the plugin at enable).

```java
import net.zbrunoc.api.DeathLogAPI;

if (DeathLogAPI.isAvailable()) {
    DeathLogAPI.getRecentDeaths(10).forEach(entry -> {
        System.out.println(entry.getDeathId() + " " + entry.getVictimName());
    });
}
```

### Synchronous

| Method | Description |
|--------|-------------|
| `getRecentDeaths(int limit)` | Latest deaths across the configured scope |
| `getDeathsByPlayer(UUID playerId)` | History for a victim |

### Asynchronous

| Method | Returns |
|--------|---------|
| `findByIdAsync(String deathId)` | `Optional<DeathLogEntry>` |
| `getRecentLogsAsync(UUID victimUuid, int limit)` | `List<DeathLogEntry>` |
| `getLootTrackingAsync(String deathId)` | `Optional<LootTrackingInfo>` (ground drops / pickups) |
| `getPvpStatsAsync(String playerNameOrUuid)` | `PvpStats` (kills, deaths, K/D, recent fights) |

Call `DeathLogAPI.isAvailable()` before use. If death logging is disabled in config, methods throw `IllegalStateException`.

### Custom storage

Implement `DeathLogProvider` only if you replace the default backend (advanced). DeathAnnouncer registers its own provider; addon authors normally call `DeathLogAPI` only.

---

## Models

### `DeathLogEntry`

Immutable death record: IDs, victim/killer, cause, weapon, location, inventory snapshot (encoded), distance, XP, telemetry JSON, verdict flag.

- `getTelemetry()` → `DeathTelemetryData` (parsed black box, or `null`)
- `getVerdict()` → `VerdictFlag` enum (or `null`)
- `relocate(serverId, clusterGroup)` for network-aware copies

Legacy constructors remain for binary compatibility but are deprecated since 1.6.1.

### `DeathTelemetryData`

Structured snapshot from the black box (TPS, MSPT, ping, cause metadata, etc.) — built from stored JSON inside the API (no extra libraries required).

### `VerdictFlag`

Black-box verdict persisted on `DeathLogEntry`. Enum constants (also stored in the database as strings):

| Constant | Meaning (summary) |
|----------|-------------------|
| `LAG_SERVER` | Server TPS / MSPT below configured thresholds at death time |
| `HIGH_PING` | Victim ping above configured threshold |
| `ENTITY_CRAMMING` | Victim in dense entity cramming at death |
| `SUSPICIOUS_COMBAT` | Combat telemetry flagged unusual hit patterns (e.g. range / timing) |
| `CLEAN` | No alert conditions fired |

`DeathLogEntry.getVerdict()` returns `null` when the death was recorded without a verdict. Thresholds live in the plugin `config.yml` telemetry section.

### `LootTrackingInfo`

Anti-dupe state for a death ID: destroyed, collected, and still-open ground items.

### `PvpStats` / `PvpFight`

Aggregated PvP statistics and recent encounter list for a player name or UUID.

### `DeathContext`

Portable description of a death (users, cause, weapon, location). Reserved for addon APIs; the plugin currently surfaces data primarily through events and `DeathLogEntry`.

### `IUser`

Minimal player handle used in events (`getName()`, `getUniqueId()`).

---

## Threading

- **Event bus** handlers run on the thread that **posts** the event — you inherit that context in the handler.
  - **Paper / Spigot:** `PlayerDeathAnnounceEvent` and `PlayerDeathBlackBoxEvent` are posted from the `PlayerDeathEvent` handler, which runs on the **global main thread**.
  - **Folia:** the same events are posted from the victim’s **region thread** (the thread that owns the entity’s `PlayerDeathEvent`). Do not touch entities or blocks from other regions without scheduling.
- If your addon calls `EventBus.post(...)` itself, handlers run on **your** calling thread.
- **DeathLog async methods** complete on the storage executor; use `CompletableFuture` callbacks and resume on the correct region or main thread (Folia `EntityScheduler` / global region scheduler, or the Bukkit main-thread scheduler) before touching players or the world.

---

## Versioning

API artifact version tracks the parent project (`2.0.0-RELEASE`). Breaking changes to public types will bump the major/minor version documented in the main plugin changelog.

---

## Support

- [Discord](https://discord.gg/fvj5xmWafE)
- [Documentation (GitBook)](https://plugin-labs.gitbook.io/deathannouncer)
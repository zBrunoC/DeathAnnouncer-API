# DeathAnnouncer API

A lightweight and easy-to-use API for the **DeathAnnouncer** plugin. This API allows developers to hook into the death logging system to fetch global death statistics, player-specific death histories, and detailed death causes.

## Installation

You can easily add the DeathAnnouncer API to your project using [JitPack](https://jitpack.io/).

### Maven

Add the JitPack repository to your ``pom.xml``:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io/</url>
    </repository>
</repositories>
```


Add the dependency:

```xml
<dependencies>
    <dependency>
        <groupId>com.github.zBrunoC</groupId>
        <artifactId>DeathAnnouncer-API</artifactId>
        <version>1.4.4-RELEASE</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```


### Gradle

Add it in your root ``settings.gradle`` at the end of repositories:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency in your ``build.gradle``:

```gradle
dependencies {
compileOnly 'com.github.zBrunoC:DeathAnnouncer-API:1.4.4-RELEASE'
}
```


*(Note: Use ``compileOnly`` or ``compileOnlyApi`` for plugin APIs to prevent shading the API into your final jar).*

## Usage

Using the API is straightforward. Before calling any methods, always ensure that the API has been properly initialized by the core plugin.

### Code Example

Below is an example class demonstrating how to fetch and display death data:

```java
package me.example;

import net.zbrunoc.api.DeathLogAPI;
import net.zbrunoc.api.model.DeathLogEntry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class DeathLogExample {

    /**
     * Example method to demonstrate how to fetch and display death data.
     * * @param requester The player requesting the data.
     * @param targetName The name of the player to look up.
     */
    public void displayDeathStatistics(Player requester, String targetName) {

        // 1. Always check if the API is initialized to avoid IllegalStateException
        if (!DeathLogAPI.isAvailable()) {
            requester.sendMessage("§cError: DeathLogAPI is not initialized yet.");
            return;
        }

        // 2. Fetch and display the last 10 global deaths
        List<DeathLogEntry> recentDeaths = DeathLogAPI.getRecentDeaths(10);
        requester.sendMessage("§6=== Recent Deaths ===");

        if (recentDeaths.isEmpty()) {
            requester.sendMessage("§7No deaths recorded.");
        } else {
            for (DeathLogEntry death : recentDeaths) {
                requester.sendMessage(String.format("§e%s §7died due to §f%s",
                        death.getVictimName(), death.getCause()));
            }
        }

        // 3. Fetch deaths for a specific player by UUID
        UUID targetUuid = Bukkit.getOfflinePlayer(targetName).getUniqueId();
        List<DeathLogEntry> playerDeaths = DeathLogAPI.getDeathsByPlayer(targetUuid);

        requester.sendMessage("§6=== Statistics for " + targetName + " ===");
        
        if (playerDeaths.isEmpty()) {
            requester.sendMessage("§7This player has no recorded deaths.");
        } else {
            for (DeathLogEntry death : playerDeaths) {
                String killer = death.getKillerName() != null ? death.getKillerName() : "Environment";
                requester.sendMessage(String.format("§eVictim: %s §7| §cKiller: %s",
                        death.getVictimName(), killer));
            }
        }
    }
}
```


### Core Methods

Here are the primary methods available in ``DeathLogAPI``:

- ``isAvailable()``: Returns ``true`` if the plugin is loaded and the API is ready to use.

- ``getRecentDeaths(int amount)``: Returns a ``List<DeathLogEntry>`` of the most recent global deaths.

- ``getDeathsByPlayer(UUID uuid)``: Returns a ``List<DeathLogEntry>`` containing the death history of a specific player.

#### Data Model (``DeathLogEntry``)

Each death is represented by a ``DeathLogEntry`` object, which provides access to:

- ``getVictimName()`` - The name of the player who died.

- ``getKillerName()`` - The name of the killer (returns ``null`` if the death was environmental).

- ``getCause()`` - The specific cause of death.

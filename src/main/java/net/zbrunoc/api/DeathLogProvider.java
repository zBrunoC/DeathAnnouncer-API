package net.zbrunoc.api;

import net.zbrunoc.api.model.DeathLogEntry;

import java.util.List;
import java.util.UUID;

public interface DeathLogProvider {

    List<DeathLogEntry> getRecentDeaths(int limit);

    List<DeathLogEntry> getDeathsByPlayer(UUID playerId);
}

package net.zbrunoc.api;

import net.zbrunoc.api.model.DeathLogEntry;
import net.zbrunoc.api.model.LootTrackingInfo;
import net.zbrunoc.api.model.PvpStats;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DeathLogProvider {

    List<DeathLogEntry> getRecentDeaths(int limit);

    List<DeathLogEntry> getDeathsByPlayer(UUID playerId);

    default CompletableFuture<Optional<DeathLogEntry>> findByIdAsync(String deathId) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    default CompletableFuture<List<DeathLogEntry>> getRecentLogsAsync(UUID victimUuid, int limit) {
        return CompletableFuture.completedFuture(List.of());
    }

    default CompletableFuture<Optional<LootTrackingInfo>> getLootTrackingAsync(String deathId) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    default CompletableFuture<PvpStats> getPvpStatsAsync(String playerNameOrUuid) {
        return CompletableFuture.completedFuture(new PvpStats(playerNameOrUuid, 0, 0, 0, "?", List.of()));
    }
}

package net.zbrunoc.api;

import net.zbrunoc.api.model.DeathLogEntry;
import net.zbrunoc.api.model.LootTrackingInfo;
import net.zbrunoc.api.model.PvpStats;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class DeathLogAPI {

    private static DeathLogProvider provider;

    private DeathLogAPI() {}

    public static void registerProvider(DeathLogProvider impl) {
        provider = impl;
    }

    public static boolean isAvailable() {
        return provider != null;
    }

    public static List<DeathLogEntry> getRecentDeaths(int limit) {
        ensureReady();
        return provider.getRecentDeaths(limit);
    }

    public static List<DeathLogEntry> getDeathsByPlayer(UUID playerId) {
        ensureReady();
        return provider.getDeathsByPlayer(playerId);
    }

    public static CompletableFuture<Optional<DeathLogEntry>> findByIdAsync(String deathId) {
        ensureReady();
        CompletableFuture<Optional<DeathLogEntry>> future = provider.findByIdAsync(deathId);
        return future == null ? CompletableFuture.completedFuture(Optional.empty()) : future;
    }

    public static CompletableFuture<List<DeathLogEntry>> getRecentLogsAsync(UUID victimUuid, int limit) {
        ensureReady();
        CompletableFuture<List<DeathLogEntry>> future = provider.getRecentLogsAsync(victimUuid, limit);
        return future == null ? CompletableFuture.completedFuture(List.of()) : future;
    }

    public static CompletableFuture<Optional<LootTrackingInfo>> getLootTrackingAsync(String deathId) {
        ensureReady();
        CompletableFuture<Optional<LootTrackingInfo>> future = provider.getLootTrackingAsync(deathId);
        return future == null ? CompletableFuture.completedFuture(Optional.empty()) : future;
    }

    public static CompletableFuture<PvpStats> getPvpStatsAsync(String playerNameOrUuid) {
        ensureReady();
        CompletableFuture<PvpStats> future = provider.getPvpStatsAsync(playerNameOrUuid);
        return future == null
                ? CompletableFuture.completedFuture(new PvpStats(playerNameOrUuid, 0, 0, 0, "?", List.of()))
                : future;
    }

    private static void ensureReady() {
        if (provider == null) {
            throw new IllegalStateException("DeathLogAPI is not initialized");
        }
    }
}

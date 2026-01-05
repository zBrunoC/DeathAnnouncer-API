package net.zbrunoc.api;

import net.zbrunoc.api.model.DeathLogEntry;

import java.util.List;
import java.util.UUID;

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

    private static void ensureReady() {
        if (provider == null) {
            throw new IllegalStateException("DeathLogAPI is not initialized");
        }
    }
}

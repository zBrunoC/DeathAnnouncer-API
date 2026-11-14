package net.zbrunoc.api.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Anti-dupe snapshot for one death. {@code collectors} maps a player or container name to the amount taken.
 */
public record LootTrackingInfo(
        String deathId,
        int totalItems,
        int destroyedCount,
        int openCount,
        int pickedUpCount,
        Map<String, Integer> collectors
) {
    public LootTrackingInfo {
        collectors = collectors == null ? Map.of() : Collections.unmodifiableMap(new LinkedHashMap<>(collectors));
        openCount = Math.max(0, openCount);
        pickedUpCount = Math.max(0, pickedUpCount);
    }
}

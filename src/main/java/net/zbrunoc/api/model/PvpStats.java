package net.zbrunoc.api.model;

import java.util.List;

/**
 * Aggregate PvP numbers for one player name or UUID.
 */
public record PvpStats(
        String targetName,
        int kills,
        int deaths,
        double kdRatio,
        String topWeapon,
        List<PvpFight> recent
) {
    public PvpStats {
        recent = recent == null ? List.of() : List.copyOf(recent);
        if (targetName == null) {
            targetName = "";
        }
        if (topWeapon == null || topWeapon.isBlank()) {
            topWeapon = "?";
        }
    }
}

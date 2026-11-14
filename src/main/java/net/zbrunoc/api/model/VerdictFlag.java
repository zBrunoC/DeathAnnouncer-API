package net.zbrunoc.api.model;

/**
 * Black-box verdict stored with a death. Names match the values written since 1.6.1.
 */
public enum VerdictFlag {
    LAG_SERVER,
    HIGH_PING,
    ENTITY_CRAMMING,
    SUSPICIOUS_COMBAT,
    CLEAN;

    public static VerdictFlag fromStored(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return VerdictFlag.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}

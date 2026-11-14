package net.zbrunoc.api.model;

import java.util.UUID;

/**
 * Immutable death record shared with external plugins.
 * Legacy constructors keep compiling and delegate to the full record.
 */
public class DeathLogEntry {

    private final String deathId;
    private final String serverId;
    private final String clusterGroup;
    private final UUID victimUUID;
    private final String victimName;
    private final UUID killerUUID;
    private final String killerName;
    private final String cause;
    private final String weapon;
    private final String source;
    private final long timestamp;
    private final String world;
    private final int x;
    private final int y;
    private final int z;
    private final String inventoryData;
    private final double distance;
    private final int xpLevels;
    private final String telemetryJson;
    private final String verdictFlag;

    public DeathLogEntry(String deathId, String serverId, String clusterGroup,
                         UUID victimUUID, String victimName, UUID killerUUID, String killerName,
                         String cause, String weapon, String source, long timestamp,
                         String world, int x, int y, int z, String inventoryData, double distance,
                         int xpLevels, String telemetryJson, String verdictFlag) {
        this.deathId = (deathId == null || deathId.isBlank()) ? UUID.randomUUID().toString() : deathId;
        this.serverId = (serverId == null || serverId.isBlank()) ? "default" : serverId;
        this.clusterGroup = (clusterGroup == null || clusterGroup.isBlank()) ? "default" : clusterGroup;
        this.victimUUID = victimUUID;
        this.victimName = victimName;
        this.killerUUID = killerUUID;
        this.killerName = killerName;
        this.cause = cause;
        this.weapon = weapon;
        this.source = source;
        this.timestamp = timestamp;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.inventoryData = inventoryData;
        this.distance = distance;
        this.xpLevels = Math.max(0, xpLevels);
        this.telemetryJson = telemetryJson;
        this.verdictFlag = verdictFlag;
    }

    /**
     * @deprecated Use the constructor that supplies {@code deathId}, {@code serverId} and {@code clusterGroup}.
     */
    @Deprecated(since = "1.6.1")
    public DeathLogEntry(UUID victimUUID, String victimName, UUID killerUUID, String killerName,
                         String cause, String weapon, String source, long timestamp,
                         String world, int x, int y, int z, String inventoryData, double distance) {
        this(null, "default", "default", victimUUID, victimName, killerUUID, killerName,
                cause, weapon, source, timestamp, world, x, y, z, inventoryData, distance, 0, null, null);
    }

    /**
     * @deprecated Use the constructor that supplies {@code deathId}, {@code serverId} and {@code clusterGroup}.
     */
    @Deprecated(since = "1.6.1")
    public DeathLogEntry(UUID victimUUID, String victimName, UUID killerUUID, String killerName,
                         String cause, String weapon, String source, long timestamp,
                         String world, int x, int y, int z, String inventoryData) {
        this(victimUUID, victimName, killerUUID, killerName, cause, weapon, source, timestamp, world, x, y, z, inventoryData, -1.0);
    }

    public DeathLogEntry relocate(String serverId, String clusterGroup) {
        return new DeathLogEntry(deathId, serverId, clusterGroup, victimUUID, victimName, killerUUID, killerName,
                cause, weapon, source, timestamp, world, x, y, z, inventoryData, distance, xpLevels, telemetryJson, verdictFlag);
    }

    public String getDeathId() { return deathId; }
    public String getServerId() { return serverId; }
    public String getClusterGroup() { return clusterGroup; }
    public UUID getVictimUUID() { return victimUUID; }
    public String getVictimName() { return victimName; }
    public UUID getKillerUUID() { return killerUUID; }
    public String getKillerName() { return killerName; }
    public String getCause() { return cause; }
    public String getWeapon() { return weapon; }
    public String getSource() { return source; }
    public long getTimestamp() { return timestamp; }
    public String getWorld() { return world != null ? world : "world"; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    public String getInventoryData() { return inventoryData; }
    public double getDistance() { return distance; }
    public int getXpLevels() { return xpLevels; }
    public String getTelemetryJson() { return telemetryJson; }
    public String getVerdictFlag() { return verdictFlag; }

    /**
     * Typed black box, or null when this death was stored without telemetry.
     */
    public DeathTelemetryData getTelemetry() {
        return DeathTelemetryData.fromJson(telemetryJson);
    }

    /**
     * Typed verdict, or null when this death has no verdict flag.
     */
    public VerdictFlag getVerdict() {
        return VerdictFlag.fromStored(verdictFlag);
    }
}

package net.zbrunoc.api.model;

import java.util.UUID;

public class DeathLogEntry {

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

    public DeathLogEntry(UUID victimUUID, String victimName, UUID killerUUID, String killerName,
                         String cause, String weapon, String source, long timestamp,
                         String world, int x, int y, int z, String inventoryData, double distance) {
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
    }

    public DeathLogEntry(UUID victimUUID, String victimName, UUID killerUUID, String killerName,
                         String cause, String weapon, String source, long timestamp,
                         String world, int x, int y, int z, String inventoryData) {
        this(victimUUID, victimName, killerUUID, killerName, cause, weapon, source, timestamp, world, x, y, z, inventoryData, -1.0);
    }

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
}
package net.zbrunoc.api.model;

import java.time.Instant;
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

    public DeathLogEntry(
            UUID victimUUID,
            String victimName,
            UUID killerUUID,
            String killerName,
            String cause,
            String weapon,
            String source
    ) {
        this.victimUUID = victimUUID;
        this.victimName = victimName;
        this.killerUUID = killerUUID;
        this.killerName = killerName;
        this.cause = cause;
        this.weapon = weapon;
        this.source = source;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public UUID getVictimUUID() { return victimUUID; }
    public String getVictimName() { return victimName; }
    public UUID getKillerUUID() { return killerUUID; }
    public String getKillerName() { return killerName; }
    public String getCause() { return cause; }
    public String getWeapon() { return weapon; }
    public String getSource() { return source; }
    public long getTimestamp() { return timestamp; }
}
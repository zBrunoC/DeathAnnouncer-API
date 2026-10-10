package net.zbrunoc.api.model;

import net.zbrunoc.api.user.IUser;

public class DeathContext {

    private final IUser victim;
    private final IUser killer;
    private final String killerName;
    private final Object cause;
    private final Object weapon;

    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final long timestamp;

    public DeathContext(IUser victim,
                        IUser killer,
                        String killerName,
                        Object cause,
                        Object weapon) {
        this(victim, killer, killerName, cause, weapon, "world", 0.0, 0.0, 0.0, System.currentTimeMillis());
    }

    public DeathContext(IUser victim,
                        IUser killer,
                        String killerName,
                        Object cause,
                        Object weapon,
                        String world,
                        double x,
                        double y,
                        double z,
                        long timestamp) {
        this.victim = victim;
        this.killer = killer;
        this.killerName = killerName;
        this.cause = cause;
        this.weapon = weapon;
        this.world = world != null ? world : "world";
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public IUser getVictim() {
        return victim;
    }

    public IUser getKiller() {
        return killer;
    }

    public String getKillerName() {
        return killerName;
    }

    public Object getCause() {
        return cause;
    }

    public String getCauseName() {
        return cause != null ? cause.toString() : "UNKNOWN";
    }

    public Object getWeapon() {
        return weapon;
    }

    public String getWeaponName() {
        return weapon != null ? weapon.toString() : "";
    }

    public boolean hasKiller() {
        return killer != null || (killerName != null && !killerName.isEmpty());
    }

    public boolean hasWeapon() {
        return weapon != null && !weapon.toString().isEmpty();
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
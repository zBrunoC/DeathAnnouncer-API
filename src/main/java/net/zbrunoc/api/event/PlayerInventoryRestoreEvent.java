package net.zbrunoc.api.event;

import net.zbrunoc.api.model.DeathLogEntry;
import net.zbrunoc.api.user.IUser;

public class PlayerInventoryRestoreEvent implements DeathAnnouncerEvent, Cancellable {

    private final IUser admin;
    private final IUser victim;
    private final DeathLogEntry deathLog;
    private final int restoredExp;
    private boolean cancelled = false;

    public PlayerInventoryRestoreEvent(IUser admin, IUser victim, DeathLogEntry deathLog, int restoredExp) {
        this.admin = admin;
        this.victim = victim;
        this.deathLog = deathLog;
        this.restoredExp = restoredExp;
    }

    public IUser getAdmin() { return admin; }
    public IUser getVictim() { return victim; }
    public DeathLogEntry getDeathLog() { return deathLog; }
    public int getRestoredExp() { return restoredExp; }

    @Override
    public boolean isCancelled() { return cancelled; }
    @Override
    public void setCancelled(boolean cancel) { this.cancelled = cancel; }
}
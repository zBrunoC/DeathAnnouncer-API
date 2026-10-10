package net.zbrunoc.api.event;

import net.zbrunoc.api.model.DeathLogEntry;
import net.zbrunoc.api.user.IUser;

public class PlayerDeathAnnounceEvent implements DeathAnnouncerEvent, Cancellable {

    private final IUser victim;
    private final IUser killer;
    private final DeathLogEntry deathLog;
    private String message;
    private boolean cancelled = false;

    public PlayerDeathAnnounceEvent(IUser victim, IUser killer, DeathLogEntry deathLog, String message) {
        this.victim = victim;
        this.killer = killer;
        this.deathLog = deathLog;
        this.message = message;
    }

    public IUser getVictim() { return victim; }
    public IUser getKiller() { return killer; }
    public DeathLogEntry getDeathLog() { return deathLog; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public boolean isCancelled() { return cancelled; }
    @Override
    public void setCancelled(boolean cancel) { this.cancelled = cancel; }
}
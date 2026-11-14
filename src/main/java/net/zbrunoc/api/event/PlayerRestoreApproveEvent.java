package net.zbrunoc.api.event;

import net.zbrunoc.api.model.RestoreSource;

import java.util.UUID;

/**
 * Fired before a pending claim is inserted. Cancel it to stop the restore from being queued.
 */
public class PlayerRestoreApproveEvent implements DeathAnnouncerEvent, Cancellable {

    private final String deathId;
    private final UUID targetUuid;
    private final String approvedBy;
    private final RestoreSource source;
    private final int pickedUpCount;
    private boolean cancelled;

    public PlayerRestoreApproveEvent(String deathId, UUID targetUuid, String approvedBy, RestoreSource source, int pickedUpCount) {
        this.deathId = deathId;
        this.targetUuid = targetUuid;
        this.approvedBy = approvedBy;
        this.source = source == null ? RestoreSource.API : source;
        this.pickedUpCount = Math.max(0, pickedUpCount);
    }

    public String getDeathId() {
        return deathId;
    }

    public UUID getTargetUuid() {
        return targetUuid;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public RestoreSource getSource() {
        return source;
    }

    public int getPickedUpCount() {
        return pickedUpCount;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}

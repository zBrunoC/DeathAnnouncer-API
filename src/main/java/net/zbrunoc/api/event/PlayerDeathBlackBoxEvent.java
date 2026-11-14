package net.zbrunoc.api.event;

import net.zbrunoc.api.model.DeathLogEntry;
import net.zbrunoc.api.model.DeathTelemetryData;
import net.zbrunoc.api.model.VerdictFlag;

/**
 * Fired after the black box is captured and the verdict is chosen, before the death is broadcast.
 */
public class PlayerDeathBlackBoxEvent implements DeathAnnouncerEvent {

    private final DeathLogEntry deathLog;
    private final DeathTelemetryData telemetry;
    private final VerdictFlag verdict;

    public PlayerDeathBlackBoxEvent(DeathLogEntry deathLog, DeathTelemetryData telemetry, VerdictFlag verdict) {
        this.deathLog = deathLog;
        this.telemetry = telemetry;
        this.verdict = verdict == null ? VerdictFlag.CLEAN : verdict;
    }

    public DeathLogEntry getDeathLog() {
        return deathLog;
    }

    public DeathTelemetryData getTelemetry() {
        return telemetry;
    }

    public VerdictFlag getVerdict() {
        return verdict;
    }
}

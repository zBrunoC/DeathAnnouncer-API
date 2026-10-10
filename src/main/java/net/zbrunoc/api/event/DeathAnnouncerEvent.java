package net.zbrunoc.api.event;

import net.zbrunoc.api.DeathAnnouncerAPI;

/**
 * A superinterface for all DeathAnnouncer events.
 */
public interface DeathAnnouncerEvent {

    /**
     * Gets the main API instance.
     *
     * @return the API instance
     */
    default DeathAnnouncerAPI getApi() {
        return DeathAnnouncerAPI.getInstance();
    }

    /**
     * Gets the event class type.
     *
     * @return the event class
     */
    default Class<? extends DeathAnnouncerEvent> getEventType() {
        return getClass();
    }
}
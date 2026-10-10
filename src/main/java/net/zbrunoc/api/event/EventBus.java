package net.zbrunoc.api.event;

import java.util.function.Consumer;

/**
 * An event bus used to register listeners and dispatch DeathAnnouncer API events.
 */
public interface EventBus {

    /**
     * Subscribes a listener to a specific API event.
     *
     * @param eventClass the class of the event to listen for
     * @param handler    the consumer callback handling the event
     * @param <T>        the event type
     */
    <T extends DeathAnnouncerEvent> void subscribe(Class<T> eventClass, Consumer<T> handler);

    /**
     * Posts an event to all registered listeners.
     *
     * @param event the event to dispatch
     */
    void post(DeathAnnouncerEvent event);
}
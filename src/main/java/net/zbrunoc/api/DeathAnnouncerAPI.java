package net.zbrunoc.api;

import net.zbrunoc.api.event.DeathAnnouncerEvent;
import net.zbrunoc.api.event.EventBus;
import net.zbrunoc.api.model.DeathContext;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Main API entry point for DeathAnnouncer.
 * <p>
 * evaluate death contexts, and listen to plugin events via the event bus.
 */
public final class DeathAnnouncerAPI {

    private static final DeathAnnouncerAPI INSTANCE = new DeathAnnouncerAPI();
    private static final EventBus EVENT_BUS = new SimpleEventBus();

    private DeathAnnouncerAPI() {}

    /**
     * Gets the singleton instance of the API.
     *
     * @return the API instance
     */
    public static DeathAnnouncerAPI getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the global event bus used to subscribe to and dispatch API events.
     *
     * @return the event bus instance
     */
    public static EventBus getEventBus() {
        return EVENT_BUS;
    }

    /**
     * Lightweight, thread-safe implementation of {@link EventBus}.
     */
    private static class SimpleEventBus implements EventBus {
        private final Map<Class<?>, List<Consumer<?>>> listeners = new ConcurrentHashMap<>();

        @Override
        @SuppressWarnings("unchecked")
        public <T extends DeathAnnouncerEvent> void subscribe(Class<T> eventClass, Consumer<T> handler) {
            listeners.computeIfAbsent(eventClass, k -> new CopyOnWriteArrayList<>())
                    .add((Consumer<DeathAnnouncerEvent>) handler);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void post(DeathAnnouncerEvent event) {
            List<Consumer<?>> handlers = listeners.get(event.getClass());
            if (handlers != null) {
                for (Consumer<?> handler : handlers) {
                    try {
                        ((Consumer<DeathAnnouncerEvent>) handler).accept(event);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }
}
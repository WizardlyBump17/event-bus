package com.wizardlybump17.eventbus.manager;

import com.wizardlybump17.eventbus.event.Cancellable;
import com.wizardlybump17.eventbus.event.Event;
import com.wizardlybump17.eventbus.listener.EventListener;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListenerManager {

    private final @NonNull Map<Class<? extends Event>, List<EventListener<?>>> listeners = new HashMap<>();

    /**
     * <p>
     * Adds the {@link EventListener} to {@code this} manager.
     * </p>
     *
     * @param listener the listener to add
     */
    public void addListener(@NonNull EventListener<?> listener) {
        listeners.computeIfAbsent(listener.eventClass(), $ -> new ArrayList<>()).add(listener);
    }

    /**
     * <p>
     * Registers all {@link EventListener}s in the provided {@link Iterable}.
     * </p>
     *
     * @param listeners the {@link EventListener}s to register
     */
    public void addListeners(@NonNull Iterable<? extends EventListener<?>> listeners) {
        for (EventListener<?> listener : listeners)
            addListener(listener);
    }

    /**
     * <p>
     * Calls the {@link EventListener#listen(Event)} method of all registered {@link EventListener}s for that event.
     * </p>
     *
     * @param event the {@link Event} to fire
     * @return if the event was not cancelled
     */
    @SuppressWarnings("unchecked")
    public boolean fireEvent(@NonNull Event event) {
        List<EventListener<?>> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null)
            return false;

        boolean cancelled = event instanceof Cancellable cancellable && cancellable.isCancelled();
        for (EventListener<?> listener : eventListeners) {
            if (listener.ignoreCancelled() && cancelled)
                continue;

            ((EventListener<Event>) listener).listen(event);
            cancelled = event instanceof Cancellable cancellable && cancellable.isCancelled();
        }

        return !cancelled;
    }

    public void clearListeners() {
        listeners.values().forEach(List::clear);
        listeners.clear();
    }

    /**
     * <p>
     * Checks if there are no {@link EventListener}s registered.
     * </p>
     *
     * @return if there are no {@link EventListener}s registered
     */
    public boolean isEmpty() {
        if (listeners.isEmpty())
            return true;

        for (List<EventListener<?>> list : listeners.values())
            if (!list.isEmpty())
                return false;

        return true;
    }

    public void removeListener(@NonNull EventListener<?> listener) {
        listeners.computeIfPresent(listener.eventClass(), ($, listeners) -> {
            listeners.remove(listener);
            return listeners.isEmpty() ? null : listeners;
        });
    }

    public void removeListeners(@NonNull Iterable<? extends EventListener<?>> listeners) {
        for (EventListener<?> listener : listeners)
            removeListener(listener);
    }
}

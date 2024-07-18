package com.wizardlybump17.eventbus.event;

import com.wizardlybump17.eventbus.list.EventListenerList;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * Represents an event that can be called by the event bus.
 * </p>
 */
public abstract class Event {

    public @NonNull String getName() {
        return getClass().getSimpleName();
    }

    /**
     * <p>
     * Returns the description of the event. A description is a brief explanation of how the event works and is called.
     * </p>
     *
     * @return the description of the event
     */
    public @Nullable String getDescription() {
        return null;
    }

    /**
     * <p>
     * Returns the {@link com.wizardlybump17.eventbus.listener.EventListener}s that are listening to this event.
     * This method <b>MUST</b> have a {@code public static} equivalent that returns the same value.
     * </p>
     *
     * @return the {@link com.wizardlybump17.eventbus.listener.EventListener} list of this event
     * @implSpec This method <b>MUST</b> have a {@code public static} equivalent that returns the same value.
     */
    public abstract @NonNull EventListenerList<? extends Event> getListenerList();
}

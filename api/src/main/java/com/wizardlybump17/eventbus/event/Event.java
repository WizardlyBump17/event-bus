package com.wizardlybump17.eventbus.event;

import com.wizardlybump17.eventbus.manager.ListenerManager;
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
     * Calls the {@link ListenerManager#fireEvent(Event)} method of the provided {@link ListenerManager} with {@code this} {@link Event}.
     * </p>
     *
     * @param listenerManager the {@link ListenerManager} to call {@code this} {@link Event}
     * @return if the {@code this} {@link Event} was not cancelled
     */
    public boolean fire(@NonNull ListenerManager listenerManager) {
        return listenerManager.fireEvent(this);
    }
}

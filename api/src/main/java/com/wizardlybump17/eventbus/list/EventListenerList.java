package com.wizardlybump17.eventbus.list;

import com.wizardlybump17.eventbus.event.Event;
import com.wizardlybump17.eventbus.listener.EventListener;
import lombok.NonNull;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class EventListenerList<E extends Event> {

    private final @NonNull TreeSet<EventListener<E>> listeners = new TreeSet<>();

    @SuppressWarnings("unchecked")
    public void addListener(@NonNull EventListener<?> listener) {
        listeners.add((EventListener<E>) listener);
    }

    public void removeListener(@NonNull EventListener<?> listener) {
        listeners.remove(listener);
    }

    public boolean hasListener(@NonNull EventListener<?> listener) {
        return listeners.contains(listener);
    }

    public void clear() {
        listeners.clear();
    }

    public @NonNull Set<EventListener<E>> getListeners() {
        return Collections.unmodifiableSortedSet(listeners);
    }

    public void fire(@NonNull Event event) {
        for (EventListener<E> listener : listeners)
            listener.listen(listener.eventClass().cast(event));
    }
}

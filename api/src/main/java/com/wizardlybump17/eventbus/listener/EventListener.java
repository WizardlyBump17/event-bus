package com.wizardlybump17.eventbus.listener;

import com.wizardlybump17.eventbus.event.Event;
import lombok.NonNull;

import java.util.function.Consumer;

public interface EventListener<E extends Event> extends Comparable<EventListener<E>> {

    void listen(@NonNull E event);

    int priority();

    boolean ignoreCancelled();

    @Override
    default int compareTo(@NonNull EventListener<E> other) {
        return Integer.compare(other.priority(), priority());
    }

    static <E extends Event> @NonNull EventListener<E> of(@NonNull Consumer<E> eventConsumer, int priority, boolean ignoreCancelled) {
        return new EventListener<>() {

            @Override
            public void listen(@NonNull E event) {
                eventConsumer.accept(event);
            }

            @Override
            public int priority() {
                return priority;
            }

            @Override
            public boolean ignoreCancelled() {
                return ignoreCancelled;
            }
        };
    }

    static <E extends Event> @NonNull EventListener<E> of(@NonNull Consumer<E> eventConsumer, @NonNull ListenerPriority priority, boolean ignoreCancelled) {
        return of(eventConsumer, priority.getPriority(), ignoreCancelled);
    }
}

package com.wizardlybump17.eventbus.listener;

import com.wizardlybump17.eventbus.event.Event;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

public interface EventListener<E extends Event> extends Comparable<EventListener<E>> {

    void listen(@NonNull E event);

    int priority();

    boolean ignoreCancelled();

    @Override
    default int compareTo(@NonNull EventListener<E> other) {
        return Integer.compare(other.priority(), priority());
    }

    @NonNull
    Class<E> eventClass();

    static <E extends Event> @NonNull EventListener<E> of(@NonNull Class<E> eventClass, @NonNull Consumer<E> eventConsumer, int priority, boolean ignoreCancelled) {
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

            @Override
            public @NonNull Class<E> eventClass() {
                return eventClass;
            }
        };
    }

    static <E extends Event> @NonNull EventListener<E> of(@NonNull Class<E> eventClass, @NonNull Consumer<E> eventConsumer, @NonNull ListenerPriority priority, boolean ignoreCancelled) {
        return of(eventClass, eventConsumer, priority.getPriority(), ignoreCancelled);
    }

    static <E extends Event> @NonNull Builder<E> builder() {
        return new Builder<>();
    }

    @Setter
    @Getter
    @Accessors(chain = true, fluent = true)
    class Builder<E extends Event> {

        Builder() {
        }

        private Class<E> eventClass;
        private Consumer<E> eventConsumer;
        private int priority;
        private boolean ignoreCancelled;

        public @NonNull BasicEventListener<E> build() {
            return new BasicEventListener<>(eventClass, eventConsumer, priority, ignoreCancelled);
        }
    }
}

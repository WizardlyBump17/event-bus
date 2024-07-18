package com.wizardlybump17.eventbus.listener;

import com.wizardlybump17.eventbus.event.Event;
import lombok.NonNull;

import java.util.function.Consumer;

public record BasicEventListener<E extends Event>(@NonNull Class<E> eventClass, @NonNull Consumer<E> eventConsumer, int priority, boolean ignoreCancelled) implements EventListener<E> {

    @Override
    public void listen(@NonNull E event) {
        eventConsumer.accept(event);
    }
}

package com.wizardlybump17.eventbus.listener;

import com.wizardlybump17.eventbus.event.Event;
import lombok.NonNull;

import java.lang.invoke.MethodHandle;
import java.util.logging.Level;
import java.util.logging.Logger;

public record MethodEventListener<E extends Event>(@NonNull Class<E> eventClass, @NonNull MethodHandle methodHandle, int priority, boolean ignoreCancelled) implements EventListener<E> {

    public static final @NonNull Logger LOGGER = Logger.getLogger("MethodEventListener");

    @Override
    public void listen(@NonNull E event) {
        try {
            methodHandle.invoke(event);
        } catch (Throwable throwable) {
            LOGGER.log(Level.SEVERE, "Error while invoking the method", throwable);
        }
    }
}

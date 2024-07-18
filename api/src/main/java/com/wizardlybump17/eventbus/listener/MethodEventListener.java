package com.wizardlybump17.eventbus.listener;

import com.wizardlybump17.eventbus.annotation.Listener;
import com.wizardlybump17.eventbus.event.Event;
import lombok.NonNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
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

    @SuppressWarnings("unchecked")
    public static @NonNull List<MethodEventListener<?>> getListeners(@NonNull Object object) {
        List<MethodEventListener<?>> listeners = new ArrayList<>();

        Class<?> clazz = object.getClass();
        for (Method method : clazz.getMethods()) {
            if (!method.isAnnotationPresent(Listener.class))
                continue;

            Listener listener = method.getAnnotation(Listener.class);
            int priority = Math.max(listener.intPriority(), listener.priority().getValue());

            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 1 || !Event.class.isAssignableFrom(parameters[0])) {
                LOGGER.log(Level.WARNING, "The method " + clazz.getName() + "#" + method.getName() + " has been marked as a listener, but it does not have a single parameter of the type Event.");
                continue;
            }

            MethodHandle handle;
            try {
                handle = MethodHandles.lookup().findVirtual(clazz, method.getName(), MethodType.methodType(method.getReturnType(), parameters));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                LOGGER.log(Level.SEVERE, "Error while fetching the method " + clazz.getName() + "#" + method.getName(), e);
                continue;
            }

            MethodEventListener<?> eventListener = new MethodEventListener<>((Class<? extends Event>) parameters[0], handle, priority, listener.ignoreCancelled());
            listeners.add(eventListener);
        }

        return listeners;
    }
}

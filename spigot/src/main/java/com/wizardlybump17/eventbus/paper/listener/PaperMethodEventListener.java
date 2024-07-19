package com.wizardlybump17.eventbus.paper.listener;

import com.wizardlybump17.eventbus.annotation.Listener;
import com.wizardlybump17.eventbus.listener.EventListener;
import com.wizardlybump17.eventbus.listener.MethodEventListener;
import com.wizardlybump17.eventbus.paper.event.PaperEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Accessors(fluent = true)
public class PaperMethodEventListener extends PaperEventListener {

    public static final @NonNull Logger LOGGER = Logger.getLogger("PaperMethodEventListener");

    private final @NonNull MethodHandle methodHandle;
    private final @NonNull Object object;

    public PaperMethodEventListener(int priority, boolean ignoreCancelled, @NonNull Class<? extends Event> originalEventType, @NonNull MethodHandle methodHandle, @NonNull Object object) {
        super(priority, ignoreCancelled, originalEventType);
        this.methodHandle = methodHandle;
        this.object = object;
    }

    @Override
    public void listen(@NonNull PaperEvent event) {
        if (!isValid(event))
            return;

        try {
            methodHandle.invokeWithArguments(object, event.getOriginalEvent());
        } catch (Throwable throwable) {
            LOGGER.log(Level.SEVERE, "Error while invoking the method", throwable);
        }
    }

    @SuppressWarnings("unchecked")
    public static @NonNull List<EventListener<?>> getListeners(@NonNull Object object) {
        List<EventListener<?>> listeners = new ArrayList<>();

        Class<?> clazz = object.getClass();
        for (Method method : clazz.getMethods()) {
            if (!method.isAnnotationPresent(Listener.class))
                continue;

            Listener listener = method.getAnnotation(Listener.class);
            int priority = Math.max(listener.intPriority(), listener.priority().getValue());

            boolean paper;

            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 1) {
                LOGGER.log(Level.WARNING, "The method " + clazz.getName() + "#" + method.getName() + " has been marked as a listener, but it does not have a single parameter of the type Event.");
                continue;
            }

            if (Event.class.isAssignableFrom(parameters[0]))
                paper = true;
            else if (!com.wizardlybump17.eventbus.event.Event.class.isAssignableFrom(parameters[0])) {
                LOGGER.log(Level.WARNING, "The method " + clazz.getName() + "#" + method.getName() + " has been marked as a listener, but it does not have a single parameter of the type Event.");
                continue;
            } else
                paper = false;

            MethodHandle handle;
            try {
                handle = MethodHandles.lookup().findVirtual(clazz, method.getName(), MethodType.methodType(method.getReturnType(), parameters));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                LOGGER.log(Level.SEVERE, "Error while fetching the method " + clazz.getName() + "#" + method.getName(), e);
                continue;
            }

            if (paper) {
                PaperMethodEventListener eventListener = new PaperMethodEventListener(priority, listener.ignoreCancelled(), (Class<? extends Event>) parameters[0], handle, object);
                listeners.add(eventListener);
                continue;
            }

            MethodEventListener<?> eventListener = new MethodEventListener<>((Class<? extends com.wizardlybump17.eventbus.event.Event>) parameters[0], handle, object, priority, listener.ignoreCancelled());
            listeners.add(eventListener);
        }

        return listeners;
    }
}

package com.wizardlybump17.eventbus.manager;

import com.wizardlybump17.eventbus.event.Event;
import com.wizardlybump17.eventbus.exception.RegisterListenerException;
import com.wizardlybump17.eventbus.list.EventListenerList;
import com.wizardlybump17.eventbus.listener.EventListener;
import lombok.NonNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class EventManager {

    public void addListener(@NonNull EventListener<?> listener) throws RegisterListenerException {
        try {
            EventListenerList<?> listeners = (EventListenerList<?>) MethodHandles.lookup().findStatic(listener.eventClass(), "getListenersList", MethodType.methodType(EventListenerList.class)).invoke();
            listeners.addListener(listener);
        } catch (Throwable throwable) {
            throw new RegisterListenerException("Failed to register a listener for the event " + listener.eventClass().getName(), throwable);
        }
    }

    public void fire(@NonNull Event event) {
        event.getListenerList().fire(event);
    }
}

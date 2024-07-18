package com.wizardlybump17.eventbus.manager;

import com.wizardlybump17.eventbus.event.Event;
import com.wizardlybump17.eventbus.exception.RegisterListenerException;
import com.wizardlybump17.eventbus.list.EventListenerList;
import com.wizardlybump17.eventbus.listener.EventListener;
import lombok.NonNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListenerManager {

    private final @NonNull List<EventListenerList<?>> allListenerLists = new LinkedList<>();

    /**
     * <p>
     * Adds an {@link EventListener} to the {@link Event} class that the listener listens to.
     * It is achieved by invoking the {@code getListenersList} static method of the {@link Event} class.
     * If that method is not found, a {@link RegisterListenerException} is thrown.
     * </p>
     *
     * @param listener the listener to add
     * @throws RegisterListenerException if the {@code getListenersList} method is not found, is not accessible or an error occurs while trying to add the listener
     */
    public void addListener(@NonNull EventListener<?> listener) throws RegisterListenerException {
        try {
            EventListenerList<?> listeners = (EventListenerList<?>) MethodHandles.lookup().findStatic(listener.eventClass(), "getListenersList", MethodType.methodType(EventListenerList.class)).invoke();
            listeners.addListener(listener);

            allListenerLists.add(listeners);
        } catch (NoSuchMethodException e) {
            throw new RegisterListenerException("The event class " + listener.eventClass().getName() + " does not have a public static method called getListenersList that returns an EventListenerList instance", e);
        } catch (IllegalAccessException e) {
            throw new RegisterListenerException("The method getListenersList of the event class " + listener.eventClass().getName() + " is not accessible", e);
        } catch (Throwable throwable) {
            throw new RegisterListenerException("An error occurred while trying to add a listener to the event class " + listener.eventClass().getName(), throwable);
        }
    }

    /**
     * <p>
     * Registers all {@link EventListener}s in the provided {@link Iterable}.
     * If an error occurs while trying to register an {@link EventListener}, a {@link RegisterListenerException} is thrown, but the other {@link EventListener}s are still registered.
     * </p>
     *
     * @param listeners the {@link EventListener}s to register
     */
    public void addListeners(@NonNull Iterable<? extends EventListener<?>> listeners) {
        List<EventListener<?>> failedListeners = new ArrayList<>();

        for (EventListener<?> listener : listeners) {
            try {
                addListener(listener);
            } catch (RegisterListenerException e) {
                failedListeners.add(listener);
            }
        }

        if (!failedListeners.isEmpty())
            throw new RegisterListenerException("An error occurred while trying to add these listeners: " + failedListeners);
    }

    /**
     * <p>
     * Calls the {@link EventListener#listen(Event)} method of all {@link EventListener}s in the {@link Event#getListenerList()} of the event.
     * </p>
     *
     * @param event the {@link Event} to fire
     * @return if the event was cancelled
     * @see EventListenerList#fireEvent(Event)
     */
    public boolean fireEvent(@NonNull Event event) {
        return event.getListenerList().fireEvent(event);
    }

    public void clearListeners() {
        for (EventListenerList<?> listenerList : allListenerLists)
            listenerList.clear();
        allListenerLists.clear();
    }

    /**
     * <p>
     * Checks if all known {@link Event}s (i.g. the ones that had an {@link EventListener} added using the {@link #addListener(EventListener)} method)have no listeners
     * </p>
     * @return if all known {@link Event}s have no listeners
     */
    public boolean isEmpty() {
        if (allListenerLists.isEmpty())
            return true;

        for (EventListenerList<?> listenerList : allListenerLists)
            if (!listenerList.isEmpty())
                return false;

        return false;
    }
}

package com.wizardlybump17.eventbus.test.event;

import com.wizardlybump17.eventbus.event.Cancellable;
import com.wizardlybump17.eventbus.event.Event;
import com.wizardlybump17.eventbus.list.EventListenerList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CancellableChangeStringEvent extends Event implements Cancellable {

    private static final @NonNull EventListenerList<CancellableChangeStringEvent> LISTENER_LIST = new EventListenerList<>();

    private @NonNull String string;
    private boolean cancelled;

    @Override
    public @NonNull EventListenerList<? extends Event> getListenerList() {
        return LISTENER_LIST;
    }

    public static @NonNull EventListenerList<CancellableChangeStringEvent> getListenersList() {
        return LISTENER_LIST;
    }
}

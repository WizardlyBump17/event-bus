package com.wizardlybump17.eventbus.test.event;

import com.wizardlybump17.eventbus.event.Event;
import com.wizardlybump17.eventbus.list.EventListenerList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChangeStringEvent extends Event {

    private static final @NonNull EventListenerList<ChangeStringEvent> LISTENER_LIST = new EventListenerList<>();

    private @NonNull String string;

    @Override
    public @NonNull EventListenerList<ChangeStringEvent> getListenerList() {
        return LISTENER_LIST;
    }

    public static @NonNull EventListenerList<ChangeStringEvent> getListenersList() {
        return LISTENER_LIST;
    }
}

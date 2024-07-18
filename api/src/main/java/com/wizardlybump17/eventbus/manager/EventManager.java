package com.wizardlybump17.eventbus.manager;

import com.wizardlybump17.eventbus.event.Event;
import lombok.NonNull;

public class EventManager {

    public void fire(@NonNull Event event) {
        event.getListenerList().fire(event);
    }
}

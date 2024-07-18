package com.wizardlybump17.eventbus.test;

import com.wizardlybump17.eventbus.listener.EventListener;
import com.wizardlybump17.eventbus.listener.ListenerPriority;
import com.wizardlybump17.eventbus.manager.EventManager;
import com.wizardlybump17.eventbus.test.event.ChangeStringEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EventTests {

    @Test
    void testChangeStringEvent() {
        EventManager eventManager = new EventManager();
        eventManager.addListener(EventListener.of(
                ChangeStringEvent.class,
                event -> {
                    System.out.println("ChangeStringEvent: " + event.getString());
                },
                ListenerPriority.NORMAL,
                false
        ));

        ChangeStringEvent event = new ChangeStringEvent("Hello World!");
        eventManager.fire(event);

        Assertions.assertEquals("Hello World!", event.getString());
    }
}

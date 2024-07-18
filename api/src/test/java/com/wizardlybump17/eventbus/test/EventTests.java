package com.wizardlybump17.eventbus.test;

import com.wizardlybump17.eventbus.listener.EventListener;
import com.wizardlybump17.eventbus.listener.ListenerPriority;
import com.wizardlybump17.eventbus.manager.ListenerManager;
import com.wizardlybump17.eventbus.test.event.ChangeStringEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EventTests {

    @Test
    void testChangeStringEvent() {
        ListenerManager listenerManager = new ListenerManager();
        listenerManager.addListener(EventListener.of(
                ChangeStringEvent.class,
                event -> {
                    System.out.println("ChangeStringEvent: " + event.getString());
                },
                ListenerPriority.NORMAL,
                false
        ));

        ChangeStringEvent event = new ChangeStringEvent("Hello World!");
        listenerManager.fire(event);

        Assertions.assertEquals("Hello World!", event.getString());
    }
}

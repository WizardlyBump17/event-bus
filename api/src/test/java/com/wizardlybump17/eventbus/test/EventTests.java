package com.wizardlybump17.eventbus.test;

import com.wizardlybump17.eventbus.listener.EventListener;
import com.wizardlybump17.eventbus.listener.ListenerPriority;
import com.wizardlybump17.eventbus.manager.ListenerManager;
import com.wizardlybump17.eventbus.test.event.CancellableChangeStringEvent;
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
        listenerManager.fireEvent(event);

        Assertions.assertEquals("Hello World!", event.getString());

        listenerManager.clearListeners();
        Assertions.assertTrue(listenerManager.isEmpty(), "The ListenerManager should be empty");

        listenerManager.addListener(EventListener.of(
                ChangeStringEvent.class,
                stringEvent -> stringEvent.setString("Hello Beautiful World!"),
                ListenerPriority.NORMAL,
                false
        ));
        event = new ChangeStringEvent("Hello World!");
        listenerManager.fireEvent(event);

        Assertions.assertEquals("Hello Beautiful World!", event.getString());
    }

    @Test
    void testCancellableChangeStringEvent() {
        ListenerManager listenerManager = new ListenerManager();
        listenerManager.addListener(EventListener.of(
                CancellableChangeStringEvent.class,
                event -> {
                    throw new UnsupportedOperationException();
                },
                ListenerPriority.NORMAL,
                true
        ));

        CancellableChangeStringEvent event = new CancellableChangeStringEvent("Hello World!");
        event.setCancelled(true);
        listenerManager.fireEvent(event);

        Assertions.assertEquals("Hello World!", event.getString());
        Assertions.assertTrue(event.isCancelled());
    }
}

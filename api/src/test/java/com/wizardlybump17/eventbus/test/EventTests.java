package com.wizardlybump17.eventbus.test;

import com.wizardlybump17.eventbus.listener.EventListener;
import com.wizardlybump17.eventbus.listener.MethodEventListener;
import com.wizardlybump17.eventbus.manager.ListenerManager;
import com.wizardlybump17.eventbus.test.event.CancellableChangeStringEvent;
import com.wizardlybump17.eventbus.test.event.ChangeStringEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EventTests {

    @Test
    void testChangeStringEvent() {
        ListenerManager listenerManager = new ListenerManager();
        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                })
                .build()
        );

        ChangeStringEvent event = new ChangeStringEvent("Hello World!");
        listenerManager.fireEvent(event);

        Assertions.assertEquals("Hello World!", event.getString());

        listenerManager.clearListeners();
        Assertions.assertTrue(listenerManager.isEmpty(), "The ListenerManager should be empty");

        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(stringEvent -> stringEvent.setString("Hello Beautiful World!"))
                .build()
        );
        event = new ChangeStringEvent("Hello World!");
        listenerManager.fireEvent(event);

        Assertions.assertEquals("Hello Beautiful World!", event.getString());
    }

    @Test
    void testCancellableChangeStringEvent() {
        ListenerManager listenerManager = new ListenerManager();
        listenerManager.addListener(EventListener.<CancellableChangeStringEvent>builder()
                .eventClass(CancellableChangeStringEvent.class)
                .eventConsumer(event -> {
                    throw new UnsupportedOperationException();
                })
                .ignoreCancelled(true)
                .build()
        );
        listenerManager.addListener(EventListener.<CancellableChangeStringEvent>builder()
                .eventClass(CancellableChangeStringEvent.class)
                .eventConsumer(event -> {
                })
                .build()
        );

        CancellableChangeStringEvent event = new CancellableChangeStringEvent("Hello World!");
        event.setCancelled(true);

        Assertions.assertDoesNotThrow(() -> listenerManager.fireEvent(event));
        Assertions.assertTrue(event.isCancelled());

        listenerManager.clearListeners();
        listenerManager.addListener(EventListener.<CancellableChangeStringEvent>builder()
                .eventClass(CancellableChangeStringEvent.class)
                .eventConsumer(stringEvent -> {
                    throw new UnsupportedOperationException();
                })
                .build()
        );
        Assertions.assertThrows(UnsupportedOperationException.class, () -> listenerManager.fireEvent(event));
    }

    @Test
    void testMethodListeners() {
        ListenerManager listenerManager = new ListenerManager();
        listenerManager.addListeners(MethodEventListener.getListeners(new TestObject()));

        ChangeStringEvent event = new ChangeStringEvent("Hello World!");
        listenerManager.fireEvent(event);

        Assertions.assertEquals("Working", event.getString());
    }
}

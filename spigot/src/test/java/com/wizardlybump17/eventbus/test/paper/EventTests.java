package com.wizardlybump17.eventbus.test.paper;

import com.wizardlybump17.eventbus.annotation.Listener;
import com.wizardlybump17.eventbus.manager.ListenerManager;
import com.wizardlybump17.eventbus.paper.event.PaperEvent;
import com.wizardlybump17.eventbus.paper.listener.PaperMethodEventListener;
import com.wizardlybump17.eventbus.test.paper.event.ChangeStringEvent;
import com.wizardlybump17.eventbus.test.paper.event.PaperChangeIntegerEvent;
import com.wizardlybump17.eventbus.test.paper.event.PaperChangeStringEvent;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTests {

    @Test
    void testEvents() {
        ListenerManager manager = new ListenerManager();

        ChangeStringEvent wb17Event = new ChangeStringEvent("Hello World");
        PaperChangeStringEvent paperEvent = new PaperChangeStringEvent("Hello World");

        manager.addListeners(PaperMethodEventListener.getListeners(new TestObject()));

        manager.fireEvent(wb17Event);
        assertEquals("Hello World", wb17Event.getString());

        manager.fireEvent(new PaperEvent(paperEvent));
        manager.fireEvent(new PaperEvent(new PaperChangeIntegerEvent(10)));
        assertEquals("Paper", paperEvent.getString());
    }

    @Test
    void testCancellation() {
        ListenerManager manager = new ListenerManager();

        manager.addListeners(PaperMethodEventListener.getListeners(new CancellationTest()));

        PaperChangeStringEvent event = new PaperChangeStringEvent("Hello World");
        event.setCancelled(true);

        assertDoesNotThrow(() -> manager.fireEvent(new PaperEvent(event)));
    }

    public static class CancellationTest {

        @Listener(ignoreCancelled = true)
        public void onEvent(@NonNull PaperChangeStringEvent event) {
            throw new UnsupportedOperationException();
        }
    }
}

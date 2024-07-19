package com.wizardlybump17.eventbus.test.paper;

import com.wizardlybump17.eventbus.manager.ListenerManager;
import com.wizardlybump17.eventbus.paper.event.PaperEvent;
import com.wizardlybump17.eventbus.paper.listener.PaperMethodEventListener;
import com.wizardlybump17.eventbus.test.paper.event.ChangeStringEvent;
import com.wizardlybump17.eventbus.test.paper.event.PaperChangeIntegerEvent;
import com.wizardlybump17.eventbus.test.paper.event.PaperChangeStringEvent;
import org.junit.jupiter.api.Test;

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
}

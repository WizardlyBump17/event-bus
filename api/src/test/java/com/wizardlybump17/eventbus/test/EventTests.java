package com.wizardlybump17.eventbus.test;

import com.wizardlybump17.eventbus.annotation.Listener;
import com.wizardlybump17.eventbus.listener.BasicEventListener;
import com.wizardlybump17.eventbus.listener.EventListener;
import com.wizardlybump17.eventbus.listener.ListenerPriority;
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

    @Test
    void testPriority() {
        ListenerManager listenerManager = new ListenerManager();
        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                    Assertions.assertEquals("Hello World!", event.getString());
                    event.setString("Lowest");
                })
                .priority(ListenerPriority.LOWEST)
                .build()
        );
        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                    Assertions.assertEquals("Lowest", event.getString());
                    event.setString("Low");
                })
                .priority(ListenerPriority.LOW)
                .build()
        );
        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                    Assertions.assertEquals("Low", event.getString());
                    event.setString("Normal");
                })
                .priority(ListenerPriority.NORMAL)
                .build()
        );
        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                    Assertions.assertEquals("Normal", event.getString());
                    event.setString("High");
                })
                .priority(ListenerPriority.HIGH)
                .build()
        );
        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                    Assertions.assertEquals("High", event.getString());
                    event.setString("51");
                })
                .priority(51)
                .build()
        );
        listenerManager.addListener(EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                    Assertions.assertEquals("51", event.getString());
                    event.setString("Highest");
                })
                .priority(ListenerPriority.HIGHEST)
                .build()
        );
        listenerManager.addListeners(MethodEventListener.getListeners(new PriorityTest()));

        ChangeStringEvent event = new ChangeStringEvent("Hello World!");
        listenerManager.fireEvent(event);

        Assertions.assertEquals("Finished", event.getString());
    }

    @Test
    void testPriorityDefaultValues() {
        clearPriorityValues();

        Assertions.assertEquals(-100, ListenerPriority.LOWEST.getValue());
        Assertions.assertEquals(-50, ListenerPriority.LOW.getValue());
        Assertions.assertEquals(0, ListenerPriority.NORMAL.getValue());
        Assertions.assertEquals(50, ListenerPriority.HIGH.getValue());
        Assertions.assertEquals(100, ListenerPriority.HIGHEST.getValue());
    }

    static void clearPriorityValues() {
        System.clearProperty(ListenerPriority.LOWEST.getPropertyKey());
        System.clearProperty(ListenerPriority.LOW.getPropertyKey());
        System.clearProperty(ListenerPriority.NORMAL.getPropertyKey());
        System.clearProperty(ListenerPriority.HIGH.getPropertyKey());
        System.clearProperty(ListenerPriority.HIGHEST.getPropertyKey());
    }

//    @Test
//    void testCustomPriorityValues() { //this test only passes if it is executed in a fresh JVM
//        System.setProperty(ListenerPriority.LOWEST.getPropertyKey(), "1");
//        System.setProperty(ListenerPriority.LOW.getPropertyKey(), "2");
//        System.setProperty(ListenerPriority.NORMAL.getPropertyKey(), "3");
//        System.setProperty(ListenerPriority.HIGH.getPropertyKey(), "4");
//        System.setProperty(ListenerPriority.HIGHEST.getPropertyKey(), "5");
//
//        Assertions.assertEquals(1, ListenerPriority.LOWEST.getValue());
//        Assertions.assertEquals(2, ListenerPriority.LOW.getValue());
//        Assertions.assertEquals(3, ListenerPriority.NORMAL.getValue());
//        Assertions.assertEquals(4, ListenerPriority.HIGH.getValue());
//        Assertions.assertEquals(5, ListenerPriority.HIGHEST.getValue());
//
//        clearPriorityValues();
//    }

    @Test
    void testEmptyListenerManager() {
        ListenerManager listenerManager = new ListenerManager();
        ChangeStringEvent event = new ChangeStringEvent("Hello World");
        Assertions.assertTrue(listenerManager.fireEvent(event));
        Assertions.assertEquals("Hello World", event.getString());
    }

    public static class PriorityTest {

        @Listener(intPriority = 101)
        public void onChangeString(ChangeStringEvent event) {
            Assertions.assertEquals("Highest", event.getString());
            event.setString("101");
        }

        @Listener(intPriority = 102)
        public void onChangeString2(ChangeStringEvent event) {
            Assertions.assertEquals("101", event.getString());
            event.setString("Finished");
        }
    }

    @Test
    void testRemoveListeners() {
        ListenerManager manager = new ListenerManager();

        BasicEventListener<ChangeStringEvent> listener1 = EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> {
                })
                .build();
        BasicEventListener<ChangeStringEvent> listener2 = EventListener.<ChangeStringEvent>builder()
                .eventClass(ChangeStringEvent.class)
                .eventConsumer(event -> event.setString("Hello World!"))
                .build();

        manager.addListener(listener1);
        manager.addListener(listener2);

        manager.removeListener(listener1);
        manager.removeListener(listener2);

        Assertions.assertTrue(manager.isEmpty(), "The ListenerManager should be empty");
    }
}

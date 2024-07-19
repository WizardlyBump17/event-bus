package com.wizardlybump17.eventbus.paper.listener;

import com.wizardlybump17.eventbus.paper.event.PaperEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;

import java.util.function.Consumer;

@Getter
@Accessors(fluent = true)
public class BasicPaperEventListener extends PaperEventListener {

    private final @NonNull Consumer<Event> eventConsumer;

    public BasicPaperEventListener(int priority, boolean ignoreCancelled, @NonNull Class<? extends Event> originalEventType, @NonNull Consumer<Event> eventConsumer) {
        super(priority, ignoreCancelled, originalEventType);
        this.eventConsumer = eventConsumer;
    }

    @Override
    public void listen(@NonNull PaperEvent event) {
        if (isValid(event))
            eventConsumer.accept(event.getOriginalEvent());
    }
}

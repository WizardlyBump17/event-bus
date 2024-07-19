package com.wizardlybump17.eventbus.paper.listener;

import com.wizardlybump17.eventbus.listener.EventListener;
import com.wizardlybump17.eventbus.paper.event.PaperEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;

@Getter
@RequiredArgsConstructor
@Accessors(fluent = true)
public abstract class PaperEventListener implements EventListener<PaperEvent> {

    private final int priority;
    private final boolean ignoreCancelled;
    private final @NonNull Class<? extends Event> originalEventType;

    public boolean isValid(@NonNull PaperEvent event) {
        return originalEventType.isAssignableFrom(event.getOriginalEvent().getClass());
    }

    @Override
    public @NonNull Class<PaperEvent> eventClass() {
        return PaperEvent.class;
    }
}

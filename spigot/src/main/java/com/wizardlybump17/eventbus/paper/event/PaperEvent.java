package com.wizardlybump17.eventbus.paper.event;

import com.wizardlybump17.eventbus.event.Cancellable;
import com.wizardlybump17.eventbus.event.Event;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PaperEvent extends Event implements Cancellable {

    private final @NonNull org.bukkit.event.Event originalEvent;

    @SuppressWarnings("unchecked")
    public <E extends org.bukkit.event.Event> @NonNull E getOriginalEvent() {
        return (E) originalEvent;
    }

    @Override
    public boolean isCancelled() {
        return originalEvent instanceof org.bukkit.event.Cancellable cancellable && cancellable.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        if (originalEvent instanceof org.bukkit.event.Cancellable cancellable)
            cancellable.setCancelled(cancelled);
    }
}

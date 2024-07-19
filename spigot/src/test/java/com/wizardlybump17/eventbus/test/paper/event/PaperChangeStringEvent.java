package com.wizardlybump17.eventbus.test.paper.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
@RequiredArgsConstructor
public class PaperChangeStringEvent extends Event implements Cancellable {

    private static final @NonNull HandlerList HANDLER_LIST = new HandlerList();

    private @NonNull String string;
    private boolean cancelled;

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NonNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}

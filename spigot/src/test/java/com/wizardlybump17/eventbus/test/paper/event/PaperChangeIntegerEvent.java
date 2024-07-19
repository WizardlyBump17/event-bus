package com.wizardlybump17.eventbus.test.paper.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class PaperChangeIntegerEvent extends Event implements Cancellable {

    private static final @NonNull HandlerList HANDLER_LIST = new HandlerList();

    private int integer;
    private boolean cancelled;

    public PaperChangeIntegerEvent(int integer) {
        this.integer = integer;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NonNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}

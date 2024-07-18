package com.wizardlybump17.eventbus.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}

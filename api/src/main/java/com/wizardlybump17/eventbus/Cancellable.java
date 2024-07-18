package com.wizardlybump17.eventbus;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}

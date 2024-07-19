package com.wizardlybump17.eventbus.test.event;

import com.wizardlybump17.eventbus.event.Cancellable;
import com.wizardlybump17.eventbus.event.Event;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CancellableChangeStringEvent extends Event implements Cancellable {

    private @NonNull String string;
    private boolean cancelled;
}

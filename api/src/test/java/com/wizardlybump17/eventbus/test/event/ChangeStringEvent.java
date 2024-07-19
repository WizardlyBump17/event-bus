package com.wizardlybump17.eventbus.test.event;

import com.wizardlybump17.eventbus.event.Event;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChangeStringEvent extends Event {

    private @NonNull String string;
}

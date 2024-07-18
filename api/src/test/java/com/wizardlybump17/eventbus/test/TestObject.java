package com.wizardlybump17.eventbus.test;

import com.wizardlybump17.eventbus.annotation.Listener;
import com.wizardlybump17.eventbus.test.event.ChangeStringEvent;
import lombok.NonNull;

public class TestObject {

    @Listener
    public void onChangeString(@NonNull ChangeStringEvent event) {
        event.setString("Working");
    }
}

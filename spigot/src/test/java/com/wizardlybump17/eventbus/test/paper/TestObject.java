package com.wizardlybump17.eventbus.test.paper;

import com.wizardlybump17.eventbus.annotation.Listener;
import com.wizardlybump17.eventbus.test.paper.event.ChangeStringEvent;
import com.wizardlybump17.eventbus.test.paper.event.PaperChangeIntegerEvent;
import com.wizardlybump17.eventbus.test.paper.event.PaperChangeStringEvent;
import lombok.NonNull;

public class TestObject {

    @Listener
    public void wb17StringChange(@NonNull ChangeStringEvent event) {
    }

    @Listener
    public void paperStringChange(@NonNull PaperChangeStringEvent event) {
        event.setString("Paper");
    }

    @Listener
    public void paperIntegerChange(@NonNull PaperChangeIntegerEvent event) {
        event.setInteger(20);
    }
}

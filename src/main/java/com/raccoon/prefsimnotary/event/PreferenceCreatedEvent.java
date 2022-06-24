package com.raccoon.prefsimnotary.event;

import com.raccoon.prefsimnotary.model.document.User;
import org.springframework.context.ApplicationEvent;

public class PreferenceCreatedEvent extends ApplicationEvent {

    public PreferenceCreatedEvent(Object source) {
        super(source);
    }

    public User getRunnerUser() {
        return (User) getSource();
    }
}
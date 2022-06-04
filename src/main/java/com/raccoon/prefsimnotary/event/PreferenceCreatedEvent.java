package com.raccoon.prefsimnotary.event;

import org.springframework.context.ApplicationEvent;

public class PreferenceCreatedEvent extends ApplicationEvent {

    public PreferenceCreatedEvent(Object source) {
        super(source);
    }
}
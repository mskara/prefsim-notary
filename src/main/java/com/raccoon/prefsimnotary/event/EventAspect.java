package com.raccoon.prefsimnotary.event;

import com.raccoon.prefsimnotary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EventAspect {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserService userService;

    @After("@annotation(PreferenceCreatedEventPublisher)")
    public void preferenceCreatedEventPublisher() {
        applicationEventPublisher.publishEvent(new PreferenceCreatedEvent(userService.getCurrentUser()));
    }

}
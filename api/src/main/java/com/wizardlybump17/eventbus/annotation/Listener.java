package com.wizardlybump17.eventbus.annotation;

import com.wizardlybump17.eventbus.listener.ListenerPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listener {

    int intPriority() default 0;

    ListenerPriority priority() default ListenerPriority.NORMAL;

    boolean ignoreCancelled() default false;
}

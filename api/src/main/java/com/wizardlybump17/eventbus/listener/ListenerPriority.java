package com.wizardlybump17.eventbus.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * An enum served as a shorthand for declaring listener priorities.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public enum ListenerPriority {

    LOWEST(-100),
    LOW(-50),
    NORMAL(0),
    HIGH(50),
    HIGHEST(100);

    private final int priority;

    public static @Nullable ListenerPriority getByPriority(int priority) {
        return switch (priority) {
            case -100 -> LOWEST;
            case -50 -> LOW;
            case 0 -> NORMAL;
            case 50 -> HIGH;
            case 100 -> HIGHEST;
            default -> null;
        };
    }
}

package com.wizardlybump17.eventbus.listener;

import lombok.Getter;
import lombok.NonNull;
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

    LOWEST(Integer.getInteger(ListenerPriority.class.getName() + ".LOWEST-priority", -100)),
    LOW(Integer.getInteger(ListenerPriority.class.getName() + ".LOW-priority", -50)),
    NORMAL(Integer.getInteger(ListenerPriority.class.getName() + ".NORMAL-priority", 0)),
    HIGH(Integer.getInteger(ListenerPriority.class.getName() + ".HIGH-priority", 50)),
    HIGHEST(Integer.getInteger(ListenerPriority.class.getName() + ".HIGHEST-priority", 100));

    private final int value;

    public @NonNull String getPropertyKey() {
        return getClass().getName() + "." + name() + "-priority";
    }

    public static @Nullable ListenerPriority getByValue(int value) {
        return switch (value) {
            case -100 -> LOWEST;
            case -50 -> LOW;
            case 0 -> NORMAL;
            case 50 -> HIGH;
            case 100 -> HIGHEST;
            default -> null;
        };
    }
}

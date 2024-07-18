package com.wizardlybump17.eventbus.exception;

import lombok.NonNull;

public class RegisterListenerException extends RuntimeException {

    public RegisterListenerException(@NonNull String message, @NonNull Throwable cause) {
        super(message, cause);
    }
}

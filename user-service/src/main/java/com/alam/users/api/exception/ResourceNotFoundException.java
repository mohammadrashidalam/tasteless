package com.alam.users.api.exception;

public class ResourceNotFoundException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ResourceNotFoundException() {
        super("Resource not found on server");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}

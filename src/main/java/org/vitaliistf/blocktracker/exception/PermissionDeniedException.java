package org.vitaliistf.blocktracker.exception;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String msg) {
        super(msg);
    }

}

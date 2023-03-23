package com.notebookapp.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class NotFoundCustomException extends Exception {

    private final String description;

    public NotFoundCustomException(String description) {
        super(description);
        this.description = description;
    }

    public static NotFoundCustomException getNotFoundException(String description) {
        log.debug(description);
        return new NotFoundCustomException(description);
    }

}

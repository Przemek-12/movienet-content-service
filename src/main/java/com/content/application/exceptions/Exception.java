package com.content.application.exceptions;

import org.springframework.http.HttpStatus;

public interface Exception {

    HttpStatus getStatus();

    String getMessage();
}

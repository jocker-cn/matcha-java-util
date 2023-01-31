package org.jokerCN.match.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultExceptionType implements ExceptionType {

    NULL(0x80000000, "null"),
    ;

    private final int code;
    private final String message;


}

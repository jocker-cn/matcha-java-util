package org.jokerCN.match.exception;

/**
 * <p>
 * When you want to use {@link RuntimeException } or {@link Exception} for flow control (such as jumping out of loops, jumping out of methods, etc.).
 * You may not need the stack information of the current exception,
 * you should use [**{@link ExceptionJump} or {@link  RuntimeExceptionJump}**] to avoid too much useless stack information.
 * </p>
 *
 * @author jocker-cn <a href="https://github.com/jocker-cn"></a>
 * @version $Id$
 * @date 2023-01-31 14:43:00
 */
public class RuntimeExceptionJump extends RuntimeException {

    protected final ExceptionType exceptionType;

    public RuntimeExceptionJump() {
        this.exceptionType = DefaultExceptionType.NULL;
    }

    public RuntimeExceptionJump(Throwable cause) {
        super(cause);
        this.exceptionType = DefaultExceptionType.NULL;
    }

    public RuntimeExceptionJump(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public RuntimeExceptionJump(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public RuntimeExceptionJump(String message, Throwable cause, ExceptionType exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public RuntimeExceptionJump(Throwable cause, ExceptionType exceptionType) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    public RuntimeExceptionJump(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionType exceptionType) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionType = exceptionType;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

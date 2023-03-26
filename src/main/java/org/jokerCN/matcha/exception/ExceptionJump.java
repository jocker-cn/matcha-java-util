package org.jokerCN.matcha.exception;

/**
 * <p>
 *     When you want to use {@link RuntimeException } or {@link Exception} for flow control (such as jumping out of loops, jumping out of methods, etc.).
 *     You may not need the stack information of the current exception,
 *     you should use {@link ExceptionJump} or {@link  RuntimeExceptionJump} to avoid too much useless stack information.
 * </p>
 *
 *
 * @author jocker-cn <a href="https://github.com/jocker-cn"></a>
 * @date 2023-01-31 14:43:00
 * @version $Id$
 */
public class ExceptionJump extends Exception{

    protected final ExceptionType exceptionType;

    public ExceptionJump() {
        this.exceptionType = DefaultExceptionType.NULL;
    }

    public ExceptionJump(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


}

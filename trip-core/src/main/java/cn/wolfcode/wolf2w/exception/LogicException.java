package cn.wolfcode.wolf2w.exception;

/**
 * 展示给用户看的异常
 */
public class LogicException extends RuntimeException {
    public LogicException(String message) {
        super(message);
    }
}

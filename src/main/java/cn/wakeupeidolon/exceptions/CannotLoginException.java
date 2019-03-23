package cn.wakeupeidolon.exceptions;

/**
 * @author Wang Yu
 * 无法登录异常
 */
public class CannotLoginException extends RuntimeException {
    public CannotLoginException() {
        super("无法登录");
    }
    
    public CannotLoginException(String message) {
        super(message);
    }
}

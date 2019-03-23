package cn.wakeupeidolon.exceptions;

/**
 * @author Wang Yu
 * 非法链接异常
 */
public class IllegalUrlException extends RuntimeException {
    public IllegalUrlException(String message) {
        super(message);
    }
    
}

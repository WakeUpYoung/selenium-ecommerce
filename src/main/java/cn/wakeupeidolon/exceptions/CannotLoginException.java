package cn.wakeupeidolon.exceptions;

/**
 * @Author Wang Yu
 * @Description
 * @Date 16:03 2019/3/20
 */
public class CannotLoginException extends RuntimeException {
    public CannotLoginException() {
        super();
    }
    
    public CannotLoginException(String message) {
        super(message);
    }
}

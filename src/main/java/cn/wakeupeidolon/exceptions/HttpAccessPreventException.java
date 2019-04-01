package cn.wakeupeidolon.exceptions;


/**
 * @author Wang Yu
 */
public class HttpAccessPreventException extends RuntimeException {
    public HttpAccessPreventException(String message) {
        super(message);
    }
}

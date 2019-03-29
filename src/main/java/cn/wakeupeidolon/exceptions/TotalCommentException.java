package cn.wakeupeidolon.exceptions;

/**
 * @author Wang Yu
 */
public class TotalCommentException extends RuntimeException {
    public TotalCommentException() {
        super("该商品总评论数过少");
    }
    
    public TotalCommentException(String message) {
        super(message);
    }
}

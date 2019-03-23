package cn.wakeupeidolon.service;

import cn.wakeupeidolon.bean.Comment;

import java.util.List;

/**
 * @author Wang Yu
 */
public interface CommentService {
    
    Comment save(Comment comment);
    
    Integer batchSave(List<Comment> comments);
    
}

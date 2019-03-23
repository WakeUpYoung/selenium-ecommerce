package cn.wakeupeidolon.service.impl;

import cn.wakeupeidolon.bean.Comment;
import cn.wakeupeidolon.dao.CommentDao;
import cn.wakeupeidolon.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wang Yu
 */
@Service
public class CommentServiceImpl implements CommentService {
    
    private final CommentDao commentDao;
    
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    
    @Override
    public Comment save(Comment comment) {
        return commentDao.save(comment);
    }
    
    @Override
    public Integer batchSave(List<Comment> comments) {
        List<Comment> commentList = commentDao.saveAll(comments);
        return commentList.size();
    }
}

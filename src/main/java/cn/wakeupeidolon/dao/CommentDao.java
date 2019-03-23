package cn.wakeupeidolon.dao;

import cn.wakeupeidolon.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wang Yu
 */
@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
}

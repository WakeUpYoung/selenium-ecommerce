package cn.wakeupeidolon.selenium.handler;

import cn.wakeupeidolon.bean.Comment;
import cn.wakeupeidolon.bean.Commodity;

import java.util.List;

/**
 * @author Wang Yu
 * <p>爬取数据</p>
 */
public interface CrawlData {
    List<Comment> getCommentList();
    
    Commodity getCommodity();
    
}

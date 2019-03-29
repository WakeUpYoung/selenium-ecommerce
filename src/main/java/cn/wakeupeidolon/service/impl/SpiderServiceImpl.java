package cn.wakeupeidolon.service.impl;

import cn.wakeupeidolon.bean.Comment;
import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.entity.taobao.RateList;
import cn.wakeupeidolon.selenium.handler.CrawlData;
import cn.wakeupeidolon.selenium.handler.CrawlHandler;
import cn.wakeupeidolon.service.CommentService;
import cn.wakeupeidolon.service.CommodityService;
import cn.wakeupeidolon.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Wang Yu
 * 对淘宝商品页面进行爬取
 */
@Service
public class SpiderServiceImpl implements SpiderService {
    
    private final CommentService commentService;
    
    private final CommodityService commodityService;
    
    @Autowired
    public SpiderServiceImpl(CommentService commentService, CommodityService commodityService) {
        this.commentService = commentService;
        this.commodityService = commodityService;
    }
    
    /**
     * 对天猫或淘宝进行爬取，并保存数据库
     * @return 总共爬取的评论数
     */
    @Override
    @Transactional
    public Integer spider(CrawlHandler handler) {
        // 登录
        handler.login();
        // 爬取基础数据
        handler.crawlCommodity();
        // 关闭谷歌webdriver
        handler.closeWindows();
        // 爬取评论
        handler.crawlComments();
        // 获取包装数据
        CrawlData crawlData = handler.getCrawlData();
        Commodity save = commodityService.save(crawlData.getCommodity());
        List<Comment> commentList = crawlData.getCommentList();
        commentList.forEach(comment -> {
            comment.setCommodityId(save.getId());
            comment.setCreateDate(new Date());
            comment.setBelievable(0);
            comment.setFake(0);
        });
    
        return commentService.batchSave(commentList);
    }
}

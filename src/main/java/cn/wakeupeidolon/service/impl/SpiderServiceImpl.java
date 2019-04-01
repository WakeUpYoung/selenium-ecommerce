package cn.wakeupeidolon.service.impl;

import cn.wakeupeidolon.bean.Comment;
import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.domain.Result;
import cn.wakeupeidolon.entity.taobao.RateList;
import cn.wakeupeidolon.enums.ErrorCode;
import cn.wakeupeidolon.selenium.handler.CrawlData;
import cn.wakeupeidolon.selenium.handler.CrawlHandler;
import cn.wakeupeidolon.service.CommentService;
import cn.wakeupeidolon.service.CommodityService;
import cn.wakeupeidolon.service.SpiderService;
import cn.wakeupeidolon.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger LOG = LoggerFactory.getLogger(SpiderServiceImpl.class);
    
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
    
    /**
     * 对所有urls进行爬取
     * @param handler Handler
     * @param urls URL列表
     * @return 总共爬取的商品数量
     */
    @Override
    public Integer crawlAll(CrawlHandler handler, List<String> urls) {
        int success = 0;
        int fail = 0;
        List<Comment> totalComment = new ArrayList<>();
        // 登录
        handler.login();
        for (int i = 0; i < urls.size(); i++){
            String url = urls.get(i);
            if (i != 0){
                handler.driver().get(url);
            }
            // 检测是否有重复
            if (commodityService.hasRepeat(UrlUtils.getParam(url, "id"), UrlUtils.checkWebType(url))){
                LOG.info("检测到数据库已有该商品, 跳过该url");
                continue;
            }
            try{
                if (fail >= 3){
                    LOG.error("连续爬取失败三次, 强制停止");
                    handler.closeWindows();
                    return success;
                }
                // 爬取基础数据
                handler.crawlCommodity();
                // 爬取评论
                handler.crawlComments();
                CrawlData crawlData = handler.getCrawlData();
                Commodity save = commodityService.save(crawlData.getCommodity());
                List<Comment> commentList = crawlData.getCommentList();
                commentList.forEach(comment -> {
                    comment.setCommodityId(save.getId());
                    comment.setCreateDate(new Date());
                    comment.setBelievable(0);
                    comment.setFake(0);
                    totalComment.add(comment);
                });
                success++;
                fail = 0;
    
            }catch (RuntimeException e){
                fail++;
                e.printStackTrace();
            }
        }
        commentService.batchSave(totalComment);
        handler.closeWindows();
        return success;
    }
    
}

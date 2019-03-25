package cn.wakeupeidolon.service.impl;

import cn.wakeupeidolon.bean.Comment;
import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.entity.taobao.RateList;
import cn.wakeupeidolon.selenium.handler.tmall.TmallSpider;
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
    
    @Override
    @Transactional
    public Integer spiderTmall(String url) {
    
        TmallSpider spider = TmallSpider.commentSpider(url);
        Commodity commodity = spider.getCommodity();
        List<RateList> rateList = spider.getRateList();
        List<Comment> commentList = new ArrayList<>();
        Commodity save = commodityService.save(commodity);
        rateList.forEach(rate -> {
            Comment comment = new Comment();
            comment.setPremiereComment(rate.getRateContent());
            comment.setAppendComment(rate.getAppendComment());
            comment.setCommodityId(save.getId());
            comment.setCreateDate(new Date());
            commentList.add(comment);
        });
    
        return commentService.batchSave(commentList);
    }
}

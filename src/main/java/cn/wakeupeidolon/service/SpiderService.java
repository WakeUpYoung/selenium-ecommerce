package cn.wakeupeidolon.service;

import cn.wakeupeidolon.selenium.handler.CrawlHandler;

import java.util.List;

/**
 * @author Wang Yu
 * 爬取数据Service
 */
public interface SpiderService {
    Integer spider(CrawlHandler handler);
    
    Integer crawlAll(CrawlHandler handler, List<String> urls);
}

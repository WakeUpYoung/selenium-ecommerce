package cn.wakeupeidolon.service;

import cn.wakeupeidolon.selenium.handler.CrawlHandler;

/**
 * @author Wang Yu
 * 爬取数据Service
 */
public interface SpiderService {
    Integer spider(CrawlHandler handler);
}

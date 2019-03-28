package cn.wakeupeidolon.selenium.handler;

import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.exceptions.CannotLoginException;
import org.openqa.selenium.WebDriver;

/**
 * @author Wang Yu
 * <p>爬取接口（门面模式）</p>
 */
public interface CrawlHandler {
    boolean login() throws CannotLoginException;
    
    void crawlCommodity();
    
    void crawlComments();
    
    CrawlData getCrawlData();
}

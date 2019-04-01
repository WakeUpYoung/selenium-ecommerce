package cn.wakeupeidolon.selenium.handler;

import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.exceptions.CannotLoginException;
import cn.wakeupeidolon.exceptions.HttpAccessPreventException;
import cn.wakeupeidolon.exceptions.TotalCommentException;
import org.openqa.selenium.WebDriver;

/**
 * @author Wang Yu
 * <p>爬取接口（门面模式）</p>
 */
public interface CrawlHandler {
    boolean login() throws CannotLoginException;
    
    void crawlCommodity() throws TotalCommentException;
    
    void crawlComments();
    
    void closeWindows();
    
    CrawlData getCrawlData();
    
    WebDriver driver();
}

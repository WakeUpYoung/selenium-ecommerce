package cn.wakeupeidolon.selenium.factory;

import cn.wakeupeidolon.enums.WebType;
import cn.wakeupeidolon.exceptions.IllegalUrlException;
import cn.wakeupeidolon.selenium.handler.CrawlHandler;
import cn.wakeupeidolon.selenium.handler.taobao.TaoBaoCrawlHandler;
import cn.wakeupeidolon.selenium.handler.tmall.TmallCrawlHandler;
import cn.wakeupeidolon.utils.UrlUtils;
import org.openqa.selenium.WebDriver;

/**
 * @author Wang Yu
 */
public class CrawlHandlerFactory {
    public static CrawlHandler create(String url) throws IllegalUrlException{
        SeleniumFactory seleniumFactory = SeleniumFactory.create(url, false);
        WebDriver driver = seleniumFactory.driver();
        int type = UrlUtils.checkWebType(url);
        if (type == WebType.TAO_BAO.getType()){
            return new TaoBaoCrawlHandler(driver);
        }
        if (type == WebType.TMALL.getType()){
            return new TmallCrawlHandler(driver);
        }
        if (type == WebType.JD.getType()){
            // TODO not yet implements
            return null;
        }
        if (type == WebType.UNKNOWN.getType()){
            throw new IllegalUrlException("非支持的电商平台url(天猫、淘宝、京东)");
        }
        throw new IllegalUrlException("非支持的电商平台url(天猫、淘宝、京东)");
    }
}

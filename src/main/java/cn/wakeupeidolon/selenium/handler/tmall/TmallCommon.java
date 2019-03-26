package cn.wakeupeidolon.selenium.handler.tmall;

import cn.wakeupeidolon.bean.Commodity;
import com.alibaba.fastjson.JSON;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 获取Tmall基础数据包括商品名、评论总数、商品评分
 * @author Wang Yu
 */
public class TmallCommon {
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallCommon.class);
    
    public static Commodity getCommonData(WebDriver driver){
        WebElement goodsName;
        try{
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1 > a"));
        
        }catch (NoSuchElementException e){
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1"));
        }
        int type = TmallLogin.taoBaoOrTmall(driver.getCurrentUrl());
        // 商品名称
        String goodsNameStr = goodsName.getText();
    
        // 可点击的评论标签
        WebElement reviewsLi = driver.findElement(By.cssSelector("#J_ItemRates"));
        reviewsLi.click();
        
        // 评论总数
        WebElement totalComment = new WebDriverWait(driver, 5)
                .until((d) -> {
                    return d.findElement(By.cssSelector("#J_TabBar > li.tm-selected > a > em"));
                });
        // 总评论数
        String totalCommentStr = totalComment.getText();
        // 商品评分
        WebElement commodityRate = new WebDriverWait(driver, 15)
                .until((d) -> {
                    return d.findElement(By.cssSelector("#J_Reviews > div > div.rate-header.rate-header-tags > div.rate-score > strong"));
                });
        // 评分
        double rate = Double.valueOf(commodityRate.getText());
        Commodity commodity = new Commodity();
        commodity.setTotalComment(Integer.valueOf(totalCommentStr));
        commodity.setCommodityName(goodsNameStr);
        commodity.setCommodityRate(rate);
        commodity.setType(type);
        commodity.setCreateDate(new Date());
        LOG.info("Crawl data : " + JSON.toJSONString(commodity));
        return commodity;
        
    }
}

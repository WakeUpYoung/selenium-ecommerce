package cn.wakeupeidolon.selenium.handler.tmall;

import cn.wakeupeidolon.bean.Commodity;
import com.alibaba.fastjson.JSON;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取淘宝基础数据包括商品名、评论总数、商品评分
 * @author Wang Yu
 */
public class TaoBaoCommon {
    private static final Logger LOG = LoggerFactory.getLogger(TaoBaoCommon.class);
    
    public static Commodity getCommonData(WebDriver driver){
        WebElement goodsName;
        goodsName = driver.findElement(By.cssSelector("#J_Title > h3"));
        int type = TmallLogin.taoBaoOrTmall(driver.getCurrentUrl());
        // 滚动脚本
        String scrollScript = "arguments[0].scrollIntoView(false);";
        // 商品名称
        String goodsNameStr = goodsName.getText();
    
        // 评论标签所在ul
        WebElement reviewUl = driver.findElement(By.cssSelector("#J_TabBar"));
        // 滚动到评论所在列
        ((JavascriptExecutor) driver).executeScript(scrollScript, reviewUl);
    
        // 可点击的评论标签
        WebElement reviewsLi = driver.findElement(By.cssSelector("#J_TabBar > li:nth-child(2)"));
        reviewsLi.click();
        // 评论总数
        WebElement totalComment = new WebDriverWait(driver, 5)
                .until((d) -> {
                    return d.findElement(By.cssSelector("#J_TabBar > li.selected > a > em"));
                });
        // 总评论数
        String totalCommentStr = totalComment.getText();
        WebElement favourCountElement = new WebDriverWait(driver, 5)
                .until(d -> {
                   return d.findElement(By.cssSelector("#reviews > div > div > div > div > div " +
                           "> div.tb-revhd > div.kg-rate-wd-filter-bar > ul > li:nth-child(4) > label > span > span.content-regarded"));
                });
        String favourCountStr = favourCountElement.getText();
        double favorableRate = -1.0;
        Pattern pattern = Pattern.compile("[\\d]+");
        Matcher matcher = pattern.matcher(favourCountStr);
        if (matcher.find()){
            String favourCount = matcher.group();
            Double favour = Double.valueOf(favourCount);
            double accurateDouble = favour / Double.valueOf(totalCommentStr) * 100;
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(accurateDouble));
            favorableRate = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    
        Commodity commodity = new Commodity();
        commodity.setTotalComment(Integer.valueOf(totalCommentStr));
        commodity.setCommodityName(goodsNameStr);
        commodity.setFavorableRate(favorableRate);
        commodity.setType(type);
        commodity.setCreateDate(new Date());
        LOG.info("Crawl Tao Bao data : " + JSON.toJSONString(commodity));
        return commodity;
        
    }
}

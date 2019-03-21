package cn.wakeupeidolon.selenium.handler.taobao;

import cn.wakeupeidolon.selenium.factory.SeleniumFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 获取基础数据包括商品名、评论总数、商品评分
 * @author Wang Yu
 */
public class TaobaoCommon {
    
    public static void getCommonData(WebDriver driver){
        WebElement goodsName;
        try{
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1 > a"));
        
        }catch (NoSuchElementException e){
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1"));
        }
        System.out.println("商品名: " + goodsName.getText());
    
        // 可点击的评论标签
        WebElement reviewsLi = driver.findElement(By.cssSelector("#J_ItemRates"));
        reviewsLi.click();
        // 滚动脚本
        // 评论总数
        WebElement totalComment = new WebDriverWait(driver, 5)
                .until((d) -> {
                    return d.findElement(By.cssSelector("#J_TabBar > li.tm-selected > a > em"));
                });
        System.out.println("总评论数: " + totalComment.getText());
        // 商品评分
        WebElement commodityRate = new WebDriverWait(driver, 15)
                .until((d) -> {
                    return d.findElement(By.cssSelector("#J_Reviews > div > div.rate-header.rate-header-tags > div.rate-score > strong"));
                });
        System.out.println("商品评分 : " + Double.valueOf(commodityRate.getText()));
    }
}

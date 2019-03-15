package cn.wakeupeidolon.selenium.handler.taobao;

import cn.wakeupeidolon.selenium.factory.SeleniumFactory;
import cn.wakeupeidolon.selenium.handler.SeleniumHandler;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


/**
 * @author Wang Yu
 */
public class TaobaoHandler implements SeleniumHandler<String, Integer> {
    
    @Override
    public Integer apply(String url) {
        WebDriver driver = SeleniumFactory.create(url, false, TmallLogin.getCookiesFromFile()).driver();
        TmallLogin.login(driver);
        WebElement goodsName;
        try{
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1 > a"));
        
        }catch (NoSuchElementException e){
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1"));
        }
        System.out.println("商品名: " + goodsName.getText());
        // 评论头
        // WebElement reviewsTitle = driver.findElement(By.cssSelector("#J_Reviews > h4"));
    
        // 可点击的评论标签
        WebElement reviewsLi = driver.findElement(By.cssSelector("#J_ItemRates"));
        reviewsLi.click();
        String scrollScript = "arguments[0].scrollIntoView(false);";
        // 滚动到评论头位置
        // ((JavascriptExecutor) driver).executeScript(scrollScript, reviewsTitle);
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
    
        int count = 1;
        int maxCount = 5;
        int totalSpider = 0;
        do {
            WebElement tbody = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#J_Reviews > div > div.rate-grid > table > tbody")));
            List<WebElement> trs = tbody.findElements(By.cssSelector("tr"));
        
            for (WebElement tr : trs){
                try{
                    // 没有追加的评论
                    WebElement reviewContent = tr.findElement(By.cssSelector("td.tm-col-master > div.tm-rate-content > div.tm-rate-fulltxt"));
                    System.out.println(reviewContent.getText());
                }catch (NoSuchElementException e){
                    // 有追加评论
                    WebElement premiereReview = tr.findElement(By.cssSelector("td.tm-col-master > div.tm-rate-premiere > div.tm-rate-content > div.tm-rate-fulltxt"));
                    WebElement appendReview = tr.findElement(By.cssSelector("td.tm-col-master > div.tm-rate-append > div.tm-rate-content > div.tm-rate-fulltxt"));
                    System.out.println("premiere : " + premiereReview.getText());
                    System.out.println("append : " + appendReview.getText());
                }
                totalSpider ++;
            }
            // 页码选择器
            WebElement pageLabel = driver.findElement(By.cssSelector("#J_Reviews > div > div.rate-page > div"));
            try{
                WebElement nextPage = new WebDriverWait(driver, 3)
                        .until(ExpectedConditions.elementToBeClickable(pageLabel.findElement(By.linkText("下一页>>"))));
                ((JavascriptExecutor) driver).executeScript(scrollScript, pageLabel);
                Thread.sleep(300);
                count ++;
                if (count < maxCount){
                    nextPage.click();
                    System.out.println("====================点击了下一页===================");
                    Thread.sleep(5000);
                }else {
                    System.out.println("爬取完毕，一共爬取了" + count + "页");
                }
            
            }catch (NoSuchElementException e){
                System.out.println("没有下一页了,一共爬取了" + count + "页");
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
        }while (count <= maxCount);
        return totalSpider;
    }
}

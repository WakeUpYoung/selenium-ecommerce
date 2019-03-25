package cn.wakeupeidolon.selenium.handler.tmall;

import cn.wakeupeidolon.selenium.factory.SeleniumFactory;
import cn.wakeupeidolon.selenium.handler.SeleniumHandler;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author Wang Yu
 * 登录后处理爬取逻辑
 */
public class TmallSeleniumHandler implements SeleniumHandler<String, Integer> {
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallSeleniumHandler.class);
    
    @Override
    public Integer apply(String url) {
        SeleniumFactory seleniumFactory = SeleniumFactory.create(url, false);
        WebDriver driver = seleniumFactory.driver();
        // 登录
        try {
            TmallLogin.login(driver);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            driver.quit();
            return -1;
        }
        // 获取基本数据
        TmallCommon.getCommonData(driver);
        String scrollScript = "arguments[0].scrollIntoView(false);";
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

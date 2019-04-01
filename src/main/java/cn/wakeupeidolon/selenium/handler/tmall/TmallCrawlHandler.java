package cn.wakeupeidolon.selenium.handler.tmall;

import cn.wakeupeidolon.bean.Comment;
import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.entity.taobao.RateDetail;
import cn.wakeupeidolon.enums.WebType;
import cn.wakeupeidolon.exceptions.CannotLoginException;
import cn.wakeupeidolon.exceptions.HttpAccessPreventException;
import cn.wakeupeidolon.exceptions.IllegalUrlException;
import cn.wakeupeidolon.exceptions.TotalCommentException;
import cn.wakeupeidolon.selenium.handler.CrawlData;
import cn.wakeupeidolon.selenium.handler.CrawlHandler;
import cn.wakeupeidolon.utils.CookiesUtils;
import cn.wakeupeidolon.utils.TmallHttp;
import cn.wakeupeidolon.utils.UrlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Wang Yu
 */
public class TmallCrawlHandler implements CrawlHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallCrawlHandler.class);
    private static final String EMPTY_COMMENT = "此用户没有填写评论!";
    private static final String SAVE_COOKIES = "tmall.obj";
    
    private WebDriver driver;
    private TmallCrawlData tmallData = new TmallCrawlData();
    
    public TmallCrawlHandler(WebDriver driver) {
        this.driver = driver;
    }
    
    @Override
    public boolean login() throws CannotLoginException {
        String currentUrl = driver.getCurrentUrl();
        String loginSccSelector = "#login-info > a.sn-login";
        String userNameTag = "#login-info > span:nth-child(1) > a.j_Username.j_UserNick.sn-user-nick";
        if (UrlUtils.checkWebType(currentUrl) != WebType.TMALL.getType()){
            LOG.error("url非法:不是天猫链接");
            throw new IllegalUrlException("url非法:不是天猫链接");
        }
        // 先从文件中读取Cookies
        Set<Cookie> cookies = CookiesUtils.getCookiesFromFile(SAVE_COOKIES);
        if (cookies != null && cookies.size() > 0){
            for (Cookie cookie : cookies){
                driver.manage().addCookie(cookie);
            }
            try {
                LOG.info("等待Cookies载入");
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.navigate().refresh();
        }
        try{
            // 等待登录成功
            WebElement userName = new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(userNameTag)));
            if (userName.getText() == null || userName.getText().equals("")){
                throw new CannotLoginException("登录未成功");
            }
            LOG.info("用户: " + userName.getText() + "登录成功");
        
        }catch (NoSuchElementException | TimeoutException | CannotLoginException e){
            LOG.info("自动登录失败，开始人工登录");
            // 开始人工登录
            WebElement loginLink = driver.findElement(By.cssSelector(loginSccSelector));
            loginLink.click();
        
            try{
                WebElement userName = new WebDriverWait(driver, 60)
                        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(userNameTag)));
                LOG.info("用户: " + userName.getText() + "登录成功");
                // 人工登录成功后，保存当前Cookies
                CookiesUtils.saveCookies(CookiesUtils.getCookies(driver), SAVE_COOKIES);
                LOG.info("保存用户Cookies : 天猫");
            }catch (NoSuchElementException noSuchEcp){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void crawlCommodity() throws TotalCommentException{
        WebElement goodsName;
        try{
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1 > a"));
        
        }catch (NoSuchElementException e){
            goodsName = driver.findElement(By.cssSelector("#J_DetailMeta > div.tm-clear > div.tb-property > div > div.tb-detail-hd > h1"));
        }
        int type = UrlUtils.checkWebType(driver.getCurrentUrl());
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
        Integer totalCount = Integer.valueOf(totalCommentStr);
        if (totalCount <= 200){
            throw new TotalCommentException("商品评论数小于200");
        }
    
        // 商品评分
        WebElement commodityRate = new WebDriverWait(driver, 15)
                .until((d) -> {
                    return d.findElement(By.cssSelector("#J_Reviews > div > div.rate-header.rate-header-tags > div.rate-score > strong"));
                });
        // 评分
        double rate = Double.valueOf(commodityRate.getText());
        Commodity commodity = new Commodity();
        commodity.setTotalComment(totalCount);
        commodity.setCommodityName(goodsNameStr);
        commodity.setCommodityRate(rate);
        commodity.setType(type);
        commodity.setItemId(UrlUtils.getParam(driver.getCurrentUrl(), "id"));
        commodity.setCreateDate(new Date());
        LOG.info("Crawl Tmall data : " + JSON.toJSONString(commodity));
        this.tmallData.setCommodity(commodity);
    }
    
    @Override
    public void crawlComments(){
        List<Comment> commentList = new ArrayList<>();
        Set<Cookie> cookiesFromFile = CookiesUtils.getCookiesFromFile(SAVE_COOKIES);
        int totalComment = tmallData.getCommodity().getTotalComment();
        int crawlPage;
        if (totalComment > 500){
            crawlPage = 15;
        }else {
            crawlPage = (int)Math.ceil(totalComment/20.0);
        }
        for (int i = 1; i <=crawlPage; i++){
            try{
                RateDetail rateDetail = TmallHttp.get(TmallHttp.createUrl(tmallData.getCommodity().getItemId(), i), cookiesFromFile);
                rateDetail.getRateList().stream()
                        .filter(rate -> {
                            return !TmallCrawlHandler.EMPTY_COMMENT.equals(rate.getRateContent());
                        })
                        .forEach(rate -> {
                            Comment comment = new Comment();
                            comment.setPremiereComment(rate.getRateContent());
                            if (rate.getAppendComment() != null && !rate.getAppendComment().equals("")){
                                comment.setAppendComment(JSONObject.parseObject(rate.getAppendComment()).getString("content"));
                            }
                            commentList.add(comment);
                        });
    
            }catch (HttpAccessPreventException e){
                LOG.error(e.getMessage());
                break;
            }
        }
        this.tmallData.setCommentList(commentList);
    }
    
    @Override
    public CrawlData getCrawlData() {
        return this.tmallData;
    }
    
    @Override
    public void closeWindows() {
        driver.quit();
    }
    
    @Override
    public WebDriver driver() {
        return this.driver;
    }
}

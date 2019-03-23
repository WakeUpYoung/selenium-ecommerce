package cn.wakeupeidolon.selenium.handler.taobao;

import cn.wakeupeidolon.enums.WebType;
import cn.wakeupeidolon.exceptions.CannotLoginException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Set;

/**
 * @author Wang Yu
 * 用于淘宝和天猫的登录
 */
public class TmallLogin {
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallLogin.class);
    
    public static boolean login(WebDriver driver) throws IllegalAccessException{
        String currentUrl = driver.getCurrentUrl();
        String loginSccSelector = "";
        String userNameTag = "";
        // 商品是淘宝的
        if (taoBaoOrTmall(currentUrl) == WebType.TAO_BAO.getType()){
            loginSccSelector = "#J_SiteNavLogin > div.site-nav-menu-hd > div.site-nav-sign > a.h";
            userNameTag = "#J_SiteNavLogin > div.site-nav-menu-hd > div.site-nav-user > a.site-nav-login-info-nick";
        }
        // 商品是天猫的
        if (taoBaoOrTmall(currentUrl) == WebType.TMALL.getType()){
            loginSccSelector = "#login-info > a.sn-login";
            userNameTag = "#login-info > span:nth-child(1) > a.j_Username.j_UserNick.sn-user-nick";
        }
        if (taoBaoOrTmall(currentUrl) == WebType.UNKNOWN.getType()){
            LOG.error("url非法:不是天猫或淘宝链接");
            throw new IllegalAccessException("url非法:不是天猫或淘宝链接");
        }
        // 先从文件中读取Cookies
        Set<Cookie> cookies = getCookiesFromFile();
        if (cookies != null && cookies.size() > 0){
            for (Cookie cookie : cookies){
                driver.manage().addCookie(cookie);
            }
            try {
                Thread.sleep(5000);
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
                saveCookies(getCookies(driver));
            }catch (NoSuchElementException noSuchEcp){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断当前商品是天猫商品还是淘宝商品
     */
    public static int taoBaoOrTmall(String currentUrl){
        // 商品是淘宝的
        if (currentUrl.startsWith("https://item.taobao.com")){
            return WebType.TAO_BAO.getType();
        }
        // 商品是天猫的
        if (currentUrl.startsWith("https://detail.tmall.com")){
            return WebType.TMALL.getType();
        }
        return WebType.UNKNOWN.getType();
    }
    
    public static Set<Cookie> getCookies(WebDriver driver){
        return driver.manage().getCookies();
    }
    
    public static void saveCookies(Set<Cookie> cookies){
        try {
            File cookiesFile = new File("cookies.obj");
            if (cookiesFile.exists()){
                cookiesFile.delete();
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(cookiesFile));
            objectOutputStream.writeObject(cookies);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static Set<Cookie> getCookiesFromFile(){
        Set<Cookie> cookies = null;
        File cookiesFile = new File("cookies.obj");
        if (!cookiesFile.exists()){
            return null;
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(cookiesFile));
            cookies = (Set<Cookie>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cookies;
    }
    
}

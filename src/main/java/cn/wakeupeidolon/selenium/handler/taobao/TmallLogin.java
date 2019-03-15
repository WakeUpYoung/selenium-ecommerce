package cn.wakeupeidolon.selenium.handler.taobao;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Wang Yu
 * 用于淘宝和天猫的登录
 */
public class TmallLogin {
    
    // 淘宝
    private static final int TAO_BAO = 0;
    // 天猫
    private static final int TMALL = 1;
    
    private static final int UNKNOWN = 2;
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallLogin.class);
    
    public static boolean login(WebDriver driver){
        String currentUrl = driver.getCurrentUrl();
        String loginSccSelector = "";
        String userNameTag = "";
        // 商品是淘宝的
        if (taoBaoOrTmall(currentUrl) == TAO_BAO){
            loginSccSelector = "#J_SiteNavLogin > div.site-nav-menu-hd > div.site-nav-sign > a.h";
            userNameTag = "#J_SiteNavLogin > div.site-nav-menu-hd > div.site-nav-user > a.site-nav-login-info-nick";
        }
        // 商品是天猫的
        if (taoBaoOrTmall(currentUrl) == TMALL){
            loginSccSelector = "#login-info > a.sn-login";
            userNameTag = "#login-info > span:nth-child(1) > a.j_Username.j_UserNick.sn-user-nick";
        }
        WebElement loginLink = driver.findElement(By.cssSelector(loginSccSelector));
        loginLink.click();
        try{
            // 等待登录成功
            WebElement userName = new WebDriverWait(driver, 60)
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(userNameTag)));
            LOG.info("用户: " + userName.getText() + "登录成功");
    
        }catch (NoSuchElementException e){
            return false;
        }
        return true;
    }
    
    /**
     * 判断当前商品是天猫商品还是淘宝商品
     */
    private static int taoBaoOrTmall(String currentUrl){
        // 商品是淘宝的
        if (currentUrl.startsWith("https://item.taobao.com")){
            return TAO_BAO;
        }
        // 商品是天猫的
        if (currentUrl.startsWith("https://detail.tmall.com")){
            return TMALL;
        }
        return UNKNOWN;
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
    
    public static Set<Cookie> getCookiesFromFile(){
        Set<Cookie> cookies = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("cookies.obj"));
            cookies = (Set<Cookie>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cookies;
    }
    
    
}

package cn.wakeupeidolon.taobao;

import cn.wakeupeidolon.selenium.factory.SeleniumFactory;
import cn.wakeupeidolon.selenium.handler.taobao.TmallLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * @author Wang Yu
 */
@RunWith(JUnit4.class)
public class CookiesTest {
    @Test
    public void testCookies(){
        WebDriver driver = SeleniumFactory
                .create("https://item.taobao.com/item.htm?spm=a219r.lm0.14.48.46105101Qmdmey&id=566603706076&ns=1&abbucket=19#detail",
                        false,
                        TmallLogin.getCookiesFromFile()).driver();
        // if (TmallLogin.login(driver)){
        //     Set<Cookie> cookies = TmallLogin.getCookies(driver);
        //     TmallLogin.saveCookies(cookies);
        // }
        System.out.println("使用Cookies 登录成功");
    }
    
    @Test
    public void printUrl(){
        WebDriver driver = SeleniumFactory
                .create("https://item.taobao.com/item.htm?spm=a219r.lm0.14.48.46105101Qmdmey&id=566603706076&ns=1&abbucket=19#detail",
                        true,
                        null).driver();
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.startsWith("https://item.taobao.com")){
            System.out.println("淘宝");
        }
        if (currentUrl.startsWith("https://detail.tmall.com")){
            System.out.println("天猫");
        }
    }
    
    @Test
    public void printCookies(){
        Set<Cookie> cookiesFromSer = TmallLogin.getCookiesFromFile();
        cookiesFromSer.forEach(cookie -> {
            System.out.println(cookie.getName() + " : " + cookie.getValue());
        });
    }
    
    
}

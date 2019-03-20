package cn.wakeupeidolon.taobao;

import cn.wakeupeidolon.selenium.factory.SeleniumFactory;
import cn.wakeupeidolon.selenium.handler.taobao.TmallHttp;
import cn.wakeupeidolon.selenium.handler.taobao.TmallLogin;
import org.junit.Ignore;
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
                        false).driver();
        if (TmallLogin.login(driver)){
            Set<Cookie> cookies = TmallLogin.getCookies(driver);
            TmallLogin.saveCookies(cookies);
        }
        System.out.println("使用Cookies 登录成功");
    }
    
    @Test
    public void printUrl(){
        WebDriver driver = SeleniumFactory
                .create("https://item.taobao.com/item.htm?spm=a219r.lm0.14.48.46105101Qmdmey&id=566603706076&ns=1&abbucket=19#detail",
                        true).driver();
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
    
    @Test
    public void loginTest(){
        WebDriver driver = SeleniumFactory.create("https://detail.tmall.com/item.htm?spm=a230r.1.14.1.33794114fqbOWw&id=571553528983&cm_id=140105335569ed55e27b&abbucket=19",
                false).driver();
        boolean login = TmallLogin.login(driver);
        System.out.println(login);
    }
    
    @Test
    public void okHttpTest(){
        TmallHttp.get("https://rate.tmall.com/list_detail_rate.htm?itemId=587578411300&sellerId=1758984938&order=3&currentPage=1&tdsourcetag=s_pctim_aiomsg");
    }
    
    
}

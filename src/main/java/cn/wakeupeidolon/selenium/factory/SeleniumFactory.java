package cn.wakeupeidolon.selenium.factory;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Set;

/**
 * @author Wang Yu
 */
public class SeleniumFactory {
    
    private SeleniumInstance seleniumInstance;
    
    private SeleniumFactory(){
    
    }
    
    /**
     *
     * @param url url
     * @param disableGpu 是否禁止GPU
     */
    public static SeleniumFactory create(String url, boolean disableGpu, Set<Cookie> cookies){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ste\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars",
                "disable-web-security");
        if (disableGpu){
            options.addArguments("--headless --disable-gpu");
        }
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        if (cookies != null || cookies.size() > 0){
            for (Cookie cookie : cookies){
                driver.manage().addCookie(cookie);
            }
            driver.navigate().refresh();
        }
    
        SeleniumFactory seleniumFactory = new SeleniumFactory();
        SeleniumInstance instance = seleniumFactory.new SeleniumInstance();
        seleniumFactory.seleniumInstance = instance;
        
        instance.driver = driver;
        return seleniumFactory;
    }
    
    public WebDriver driver(){
        return this.seleniumInstance.driver;
    }
    
    private class SeleniumInstance{
        
        private WebDriver driver;
        
        
    }
    
}

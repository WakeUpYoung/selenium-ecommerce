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
     * 创建Selenium对象
     * @param url url
     * @param disableGpu 是否禁止GPU
     * @return WebDriver
     */
    public static SeleniumFactory create(String url, boolean disableGpu){
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
        SeleniumFactory seleniumFactory = new SeleniumFactory();
        SeleniumInstance instance = seleniumFactory.new SeleniumInstance();
        seleniumFactory.seleniumInstance = instance;
        
        instance.setDriver(driver);
        return seleniumFactory;
    }
    
    public WebDriver driver(){
        return this.seleniumInstance.getDriver();
    }
    
    private class SeleniumInstance{
        private WebDriver driver;
    
        WebDriver getDriver() {
            return driver;
        }
    
        void setDriver(WebDriver driver) {
            this.driver = driver;
        }
    }
    
}

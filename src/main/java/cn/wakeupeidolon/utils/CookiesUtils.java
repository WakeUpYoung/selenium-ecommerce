package cn.wakeupeidolon.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Set;

/**
 * @author Wang Yu
 * <p>Cookies 工具，保存、读取Cookies</p>
 */
public class CookiesUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(CookiesUtils.class);
    
    private CookiesUtils() {
    }
    
    /**
     * 使用webdriver读取当前Cookies
     */
    public static Set<Cookie> getCookies(WebDriver driver){
        return driver.manage().getCookies();
    }
    
    /**
     * 将Cookies保存到文件
     */
    public static void saveCookies(Set<Cookie> cookies, String name){
        try {
            File cookiesFile = new File(name);
            if (cookiesFile.exists()){
                cookiesFile.delete();
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(cookiesFile));
            objectOutputStream.writeObject(cookies);
            objectOutputStream.flush();
            objectOutputStream.close();
            LOG.info("Cookies 永久化成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 从文件中读取Cookies
     */
    @SuppressWarnings("unchecked")
    public static Set<Cookie> getCookiesFromFile(String cookiesName){
        Set<Cookie> cookies = null;
        File cookiesFile = new File(cookiesName);
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

package cn.wakeupeidolon.utils;

import cn.wakeupeidolon.enums.WebType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析URL
 * @author Wang Yu
 */
public class UrlUtils {
    
    private UrlUtils() {
    }
    
    public static String getParam(String url, String key){
        Pattern pattern = Pattern.compile("(^|&|\\?)" + key + "=([^&]*)(&|$)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()){
            String result = matcher.group();
            return result.replaceAll("[?&=" + key + "]", "");
        }
        return null;
    }
    
    public static Integer checkWebType(String url){
        // 商品是淘宝的
        if (url.startsWith("https://item.taobao.com")){
            return WebType.TAO_BAO.getType();
        }
        // 商品是天猫的
        if (url.startsWith("https://detail.tmall.com")){
            return WebType.TMALL.getType();
        }
        if (url.startsWith("https://item.jd.com")){
            return WebType.JD.getType();
        }
        return WebType.UNKNOWN.getType();
        
    }
}

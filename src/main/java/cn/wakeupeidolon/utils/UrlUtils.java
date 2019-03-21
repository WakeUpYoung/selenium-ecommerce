package cn.wakeupeidolon.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析URL
 * @author Wang Yu
 */
public class UrlUtils {
    
    public static String getParam(String url, String key){
        Pattern pattern = Pattern.compile("(^|&)" + key + "=([^&]*)(&|$)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()){
            String result = matcher.group();
            return result.replaceAll("[&=" + key + "]", "");
        }
        return null;
    }
}

package cn.wakeupeidolon.selenium.handler.taobao;

import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Wang Yu
 */
public class TmallHttp {
    
    public static void get(String url) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30L, TimeUnit.SECONDS)
                .cookieJar(new CookieJar(){
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        
                    }
    
                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        return null;
                    }
                })
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", userAgent)
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);
        final StringBuilder stringBuilder = new StringBuilder();
        // 同步请求
        ResponseBody body = null;
        try {
            body = call.execute().body();
            if (body != null){
                stringBuilder.append(body.string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder.toString());
    }
}

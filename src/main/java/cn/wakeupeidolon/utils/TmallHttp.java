package cn.wakeupeidolon.utils;

import cn.wakeupeidolon.entity.taobao.RateDetail;
import cn.wakeupeidolon.entity.taobao.TaobaoBean;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Wang Yu
 * 使用HTTP发送请求，获取json结构的评论数据
 */
public class TmallHttp {
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallHttp.class);
    
    private static final String TMALL_URL = "https://rate.tmall.com/list_detail_rate.htm";
    
    /**
     * 创建淘宝评论Url
     * @param itemId 商品ID
     * @param page 第几页
     */
    public static String createUrl(String itemId, int page){
        return TMALL_URL + "?itemId=" + itemId + "&sellerId=1758984938&order=3&currentPage=" + page + "&tdsourcetag=s_pctim_aiomsg";
    }
    
    /**
     * 通过HTTP请求获取数据，使用Cookies
     * @param url 商品页面的url
     * @return {@link RateDetail} 评论详情
     */
    public static RateDetail get(String url, Set<org.openqa.selenium.Cookie> cookiesFile) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
    
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30L, TimeUnit.SECONDS)
                .cookieJar(new CookieJar(){
                    @Override
                    public void saveFromResponse(@Nonnull HttpUrl httpUrl, @Nonnull List<Cookie> list) {
                    
                    }
    
                    @Override
                    public List<Cookie> loadForRequest(@Nonnull HttpUrl httpUrl) {
                        LOG.info("使用Cookies请求");
                        List<Cookie> cookies = new ArrayList<>();
                        if (cookiesFile == null){
                            return null;
                        }
                        cookiesFile.forEach(cookie -> {
                            String domain = cookie.getDomain();
                            if (domain.startsWith(".")){
                                domain = domain.substring(1);
                            }
                            Cookie httpCookie = new Cookie.Builder()
                                    .name(cookie.getName())
                                    .value(cookie.getValue())
                                    .hostOnlyDomain(domain)
                                    .path(cookie.getPath())
                                    .build();
                            cookies.add(httpCookie);
                        });
                        return cookies;
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
        String bodyReqStr = stringBuilder.toString();
        String jsonStr = bodyReqStr.substring(bodyReqStr.indexOf("(") + 1, bodyReqStr.lastIndexOf(")"));
        TaobaoBean taobaoBean = JSONObject.parseObject(jsonStr, TaobaoBean.class);
        return taobaoBean.getRateDetail();
        
    }
}

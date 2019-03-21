package cn.wakeupeidolon.selenium.handler.taobao;

import cn.wakeupeidolon.entity.taobao.RateDetail;
import cn.wakeupeidolon.entity.taobao.RateList;
import cn.wakeupeidolon.selenium.factory.SeleniumFactory;
import cn.wakeupeidolon.selenium.handler.SeleniumHandler;
import cn.wakeupeidolon.utils.UrlUtils;
import com.alibaba.fastjson.JSONObject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Yu
 * 对天猫或淘宝网站的评论数据进行爬取
 */
public class TmallSpider implements SeleniumHandler<String, List<RateList>> {
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallSpider.class);
    private static final String EMPTY_COMMENT = "此用户没有填写评论!";
    
    /**
     * 获取评论对象集合
     * @param url 商品页面url
     * @return 商品评论详情对象集合
     */
    @Override
    public List<RateList> apply(String url) {
        SeleniumFactory seleniumFactory = SeleniumFactory.create(url, false);
        WebDriver driver = seleniumFactory.driver();
        // 登录
        TmallLogin.login(driver);
        // 获取基本数据
        TaobaoCommon.getCommonData(driver);
        String currentUrl = driver.getCurrentUrl();
        String itemId = UrlUtils.getParam(currentUrl, "id");
        List<RateList> rateList = new ArrayList<>();
        for (int i = 1; i <=3; i++){
            RateDetail rateDetail = TmallHttp.get(TmallHttp.createUrl(itemId, i));
            rateDetail.getRateList().stream()
                    .filter(rate -> {
                        return !TmallSpider.EMPTY_COMMENT.equals(rate.getRateContent());
                    })
                    .forEach(rate -> {
                if (rate.getAppendComment() != null && !rate.getAppendComment().equals("")){
                    rate.setAppendComment(JSONObject.parseObject(rate.getAppendComment()).getString("content"));
                }
                rateList.add(rate);
            });
        }
        return rateList;
    }
}

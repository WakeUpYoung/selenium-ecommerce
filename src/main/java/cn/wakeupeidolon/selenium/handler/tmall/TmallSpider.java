package cn.wakeupeidolon.selenium.handler.tmall;

import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.entity.taobao.RateDetail;
import cn.wakeupeidolon.entity.taobao.RateList;
import cn.wakeupeidolon.enums.WebType;
import cn.wakeupeidolon.exceptions.IllegalUrlException;
import cn.wakeupeidolon.selenium.factory.SeleniumFactory;
import cn.wakeupeidolon.utils.UrlUtils;
import com.alibaba.fastjson.JSONObject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Yu
 * 对天猫或淘宝网站的评论数据进行爬取(主)
 */
public class TmallSpider {
    
    private static final Logger LOG = LoggerFactory.getLogger(TmallSpider.class);
    private static final String EMPTY_COMMENT = "此用户没有填写评论!";
    
    private List<RateList> rateList;
    private Commodity commodity;
    
    /**
     * 获取评论对象集合
     * @param url 商品页面url
     */
    public static TmallSpider commentSpider(String url) throws IllegalUrlException{
        
        SeleniumFactory seleniumFactory = SeleniumFactory.create(url, false);
        WebDriver driver = seleniumFactory.driver();
        // 登录
        try {
            TmallLogin.login(driver);
        } catch (IllegalUrlException e) {
            LOG.error(e.getMessage());
            driver.quit();
        }
        // 获取当前Url
        String currentUrl = driver.getCurrentUrl();
        Commodity commodity;
        // 获取基本信息
        if (TmallLogin.taoBaoOrTmall(currentUrl) == WebType.TMALL.getType()){
            commodity = TmallCommon.getCommonData(driver);
        }else if (TmallLogin.taoBaoOrTmall(currentUrl) == WebType.TAO_BAO.getType()){
            commodity = TaoBaoCommon.getCommonData(driver);
        }else {
            throw new IllegalUrlException("Url不是淘宝或天猫链接");
        }
        driver.quit();
        String itemId = UrlUtils.getParam(currentUrl, "id");
        List<RateList> rateList = new ArrayList<>();
        //TODO 当前只爬取了3页，未来将会增加到10页
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
        TmallSpider tmallSpider = new TmallSpider();
        tmallSpider.setCommodity(commodity);
        tmallSpider.setRateList(rateList);
        return tmallSpider;
    }
    
    public List<RateList> getRateList() {
        return rateList;
    }
    
    public void setRateList(List<RateList> rateList) {
        this.rateList = rateList;
    }
    
    public Commodity getCommodity() {
        return commodity;
    }
    
    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }
}

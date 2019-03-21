package cn.wakeupeidolon;


import cn.wakeupeidolon.entity.taobao.RateList;
import cn.wakeupeidolon.selenium.handler.taobao.TmallSpider;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        TmallSpider spider = new TmallSpider();
        List<RateList> apply = spider.apply("https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.9.55437061DOms7T&id=5494" +
                "83761739&skuId=3914222692124&areaId=510100&user_id=1991926764&cat_id=2&is_b=1&rn=b717623efd9e8f9415a90420c58e9d1c");
    }
}

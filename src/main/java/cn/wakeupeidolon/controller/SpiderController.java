package cn.wakeupeidolon.controller;

import cn.wakeupeidolon.controller.vo.request.CrawlUrlVO;
import cn.wakeupeidolon.controller.vo.response.CrawlDoneVO;
import cn.wakeupeidolon.domain.Result;
import cn.wakeupeidolon.enums.ErrorCode;
import cn.wakeupeidolon.selenium.factory.CrawlHandlerFactory;
import cn.wakeupeidolon.selenium.handler.CrawlHandler;
import cn.wakeupeidolon.service.CommodityService;
import cn.wakeupeidolon.service.SpiderService;
import cn.wakeupeidolon.utils.UrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wang Yu
 */
@RestController
@RequestMapping("/spider")
@Api(tags = {"爬取控制器"})
public class SpiderController {
    
    private static final Logger LOG = LoggerFactory.getLogger(SpiderController.class);
    
    private final SpiderService spiderService;
    
    private final CommodityService commodityService;
    
    @Autowired
    public SpiderController(SpiderService spiderService, CommodityService commodityService) {
        this.spiderService = spiderService;
        this.commodityService = commodityService;
    }
    
    @PostMapping("/one")
    @ApiOperation("对单一网页进行爬取")
    public Result<CrawlDoneVO> crawlOne(@RequestBody @Validated CrawlUrlVO urlVO){
        String itemId = UrlUtils.getParam(urlVO.getUrl(), "id");
        if (commodityService.hasRepeat(itemId, UrlUtils.checkWebType(urlVO.getUrl()))){
            return Result.error(ErrorCode.ITEM_REPEAT);
        }
        Long start = System.currentTimeMillis();
        CrawlHandler crawlHandler = CrawlHandlerFactory.create(urlVO.getUrl());
        Integer spiderCount = spiderService.spider(crawlHandler);
        Long end = System.currentTimeMillis();
        CrawlDoneVO doneVO = new CrawlDoneVO();
        doneVO.setCount(spiderCount);
        long taking = end - start;
        doneVO.setTaking(taking);
        LOG.info("爬取成功: 耗时 : " + taking/1000.0 + "s, 共爬取" + doneVO.getCount() + "条结果");
        return Result.success(doneVO);
    }
    
}

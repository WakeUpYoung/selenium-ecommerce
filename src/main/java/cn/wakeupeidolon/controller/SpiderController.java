package cn.wakeupeidolon.controller;

import cn.wakeupeidolon.controller.vo.request.CrawlUrlVO;
import cn.wakeupeidolon.controller.vo.response.CrawlDoneVO;
import cn.wakeupeidolon.controller.vo.response.MultiCrawlDoneVO;
import cn.wakeupeidolon.domain.Result;
import cn.wakeupeidolon.enums.ErrorCode;
import cn.wakeupeidolon.selenium.factory.CrawlHandlerFactory;
import cn.wakeupeidolon.selenium.handler.CrawlHandler;
import cn.wakeupeidolon.selenium.handler.taobao.TaoBaoCrawlHandler;
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

import java.util.List;
import java.util.stream.Collectors;

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
        long start = System.currentTimeMillis();
        CrawlHandler crawlHandler = CrawlHandlerFactory.create(urlVO.getUrl());
        Integer spiderCount = spiderService.spider(crawlHandler);
        CrawlDoneVO doneVO = new CrawlDoneVO();
        doneVO.setCount(spiderCount);
        long taking = System.currentTimeMillis() - start;
        doneVO.setTaking(taking);
        LOG.info("爬取成功: 耗时 : " + taking/1000.0 + "s, 共爬取" + doneVO.getCount() + "条结果");
        return Result.success(doneVO);
    }
    
    @PostMapping("/crawlAll")
    @ApiOperation("对列表所有url进行爬取")
    public Result<MultiCrawlDoneVO> crawlAll(@RequestBody @Validated List<CrawlUrlVO> voList){
        long start = System.currentTimeMillis();
        CrawlHandler crawlHandler = CrawlHandlerFactory.create(voList.get(0).getUrl());
        MultiCrawlDoneVO doneVO = new MultiCrawlDoneVO();
        Integer result = spiderService.crawlAll(crawlHandler,
                        voList.stream().map(CrawlUrlVO::getUrl).collect(Collectors.toList()));
        long taking = System.currentTimeMillis() - start;
        doneVO.setSuccess(result);
        doneVO.setTotal(voList.size());
        doneVO.setTaking(taking);
        doneVO.setFail(voList.size() - result);
        LOG.info("爬取成功: 耗时 : " + taking/1000.0 + "s, 共成功爬取" + doneVO.getSuccess() + "个商品");
        return Result.success(doneVO);
    }
    
}

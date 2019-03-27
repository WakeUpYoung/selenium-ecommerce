package cn.wakeupeidolon.controller;

import cn.wakeupeidolon.controller.vo.request.CrawlUrlVO;
import cn.wakeupeidolon.controller.vo.response.CrawlDoneVO;
import cn.wakeupeidolon.domain.Result;
import cn.wakeupeidolon.service.SpiderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final SpiderService spiderService;
    
    @Autowired
    public SpiderController(SpiderService spiderService) {
        this.spiderService = spiderService;
    }
    
    @PostMapping("/one")
    @ApiOperation("对单一网页进行爬取")
    public Result<CrawlDoneVO> crawlOne(@RequestBody @Validated CrawlUrlVO url){
        Long start = System.currentTimeMillis();
        Integer spiderTmall = spiderService.spiderTmall(url.getUrl());
        Long end = System.currentTimeMillis();
        CrawlDoneVO doneVO = new CrawlDoneVO();
        doneVO.setCount(spiderTmall);
        doneVO.setTaking(end - start);
        return Result.success(doneVO);
    }
    
    
}

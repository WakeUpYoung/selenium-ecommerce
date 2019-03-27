package cn.wakeupeidolon.controller.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Wang Yu
 */
@ApiModel("爬取完成数据对象")
public class CrawlDoneVO implements Serializable {
    @ApiModelProperty("爬取总数")
    private Integer count;
    @ApiModelProperty("耗时")
    private Long taking;
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public Long getTaking() {
        return taking;
    }
    
    public void setTaking(Long taking) {
        this.taking = taking;
    }
}

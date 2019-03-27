package cn.wakeupeidolon.controller.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wang Yu
 */
@ApiModel("商品链接")
public class CrawlUrlVO implements Serializable {
    
    @NotNull
    @NotBlank
    @ApiModelProperty("商品链接")
    private String url;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}

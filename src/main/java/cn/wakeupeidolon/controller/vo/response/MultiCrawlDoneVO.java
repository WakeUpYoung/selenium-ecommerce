package cn.wakeupeidolon.controller.vo.response;

import io.swagger.annotations.ApiModel;

/**
 * @author Wang Yu
 */
@ApiModel("批量完成")
public class MultiCrawlDoneVO {
    private int total;
    private int success;
    private int fail;
    private long taking;
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getSuccess() {
        return success;
    }
    
    public void setSuccess(int success) {
        this.success = success;
    }
    
    public int getFail() {
        return fail;
    }
    
    public void setFail(int fail) {
        this.fail = fail;
    }
    
    public long getTaking() {
        return taking;
    }
    
    public void setTaking(long taking) {
        this.taking = taking;
    }
    
}

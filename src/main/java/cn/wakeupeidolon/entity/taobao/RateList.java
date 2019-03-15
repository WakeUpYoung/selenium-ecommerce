package cn.wakeupeidolon.entity.taobao;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Yu
 */
public class RateList {
    private int displayRateSum;
    // 评论追加
    private String appendComment;
    private Date rateDate;
    // 评论内容
    private String rateContent;
  
    // 卖家回复
    private String reply;
    private List<String> pics;
    
    public int getDisplayRateSum() {
        return displayRateSum;
    }
    
    public void setDisplayRateSum(int displayRateSum) {
        this.displayRateSum = displayRateSum;
    }
    
    public String getAppendComment() {
        return appendComment;
    }
    
    public void setAppendComment(String appendComment) {
        this.appendComment = appendComment;
    }
    
    public Date getRateDate() {
        return rateDate;
    }
    
    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }
    
    public String getRateContent() {
        return rateContent;
    }
    
    public void setRateContent(String rateContent) {
        this.rateContent = rateContent;
    }
    
    public String getReply() {
        return reply;
    }
    
    public void setReply(String reply) {
        this.reply = reply;
    }
    
    public List<String> getPics() {
        return pics;
    }
    
    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}

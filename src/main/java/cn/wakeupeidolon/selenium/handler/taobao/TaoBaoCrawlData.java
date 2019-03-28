package cn.wakeupeidolon.selenium.handler.taobao;

import cn.wakeupeidolon.bean.Comment;
import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.entity.taobao.RateList;
import cn.wakeupeidolon.selenium.handler.CrawlData;

import java.util.List;

/**
 * @author Wang Yu
 */
public class TaoBaoCrawlData implements CrawlData {
    private List<Comment> commentList;
    private Commodity commodity;
    @Override
    public List<Comment> getCommentList() {
        return this.commentList;
    }
    
    @Override
    public Commodity getCommodity() {
        return this.commodity;
    }
    
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
    
    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }
}

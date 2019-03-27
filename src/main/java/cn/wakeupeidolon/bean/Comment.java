package cn.wakeupeidolon.bean;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Wang Yu
 */
@Entity
@Table(name = "tb_comment")
@DynamicUpdate
@DynamicInsert
@Proxy(lazy = false)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "commodity_id")
    private Long commodityId;
    
    @Column(name = "premiere_comment")
    private String premiereComment;
    
    @Column(name = "append_comment")
    private String appendComment;
    
    @Column(name = "believable")
    private Integer believable;
    
    @Column(name = "fake")
    private Integer fake;
    
    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "update_date")
    private Date updateDate;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCommodityId() {
        return commodityId;
    }
    
    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }
    
    public String getPremiereComment() {
        return premiereComment;
    }
    
    public void setPremiereComment(String premiereComment) {
        this.premiereComment = premiereComment;
    }
    
    public String getAppendComment() {
        return appendComment;
    }
    
    public void setAppendComment(String appendComment) {
        this.appendComment = appendComment;
    }
    
    public Integer getBelievable() {
        return believable;
    }
    
    public void setBelievable(Integer believable) {
        this.believable = believable;
    }
    
    public Integer getFake() {
        return fake;
    }
    
    public void setFake(Integer fake) {
        this.fake = fake;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

package cn.wakeupeidolon.bean;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Wang Yu
 */
@Entity
@Table(name = "tb_commodity")
@DynamicUpdate
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "commodity_name")
    private String commodityName;
    
    @Column(name = "commodity_rate")
    private Double commodityRate;
    
    @Column(name = "favorable_rate")
    private Double favorableRate;
    
    @Column(name = "total_comment")
    private Integer totalComment;
    
    @Column(name = "type")
    private Integer type;
    
    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "update_date")
    private Date updateDate;
    
    public Double getFavorableRate() {
        return favorableRate;
    }
    
    public void setFavorableRate(Double favorableRate) {
        this.favorableRate = favorableRate;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCommodityName() {
        return commodityName;
    }
    
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
    
    public Double getCommodityRate() {
        return commodityRate;
    }
    
    public void setCommodityRate(Double commodityRate) {
        this.commodityRate = commodityRate;
    }
    
    public Integer getTotalComment() {
        return totalComment;
    }
    
    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
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

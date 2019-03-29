package cn.wakeupeidolon.service;


import cn.wakeupeidolon.bean.Commodity;

import java.util.List;

/**
 * @author Wang Yu
 */
public interface CommodityService {
    Commodity save(Commodity comment);
    
    Integer batchSave(List<Commodity> commodityList);
    
    /**
     * 检查数据库中是否已经有该商品记录
     * @return <p>如果有，返回true; 如果没有，返回false</p>
     */
    Boolean hasRepeat(String itemId, int type);
}

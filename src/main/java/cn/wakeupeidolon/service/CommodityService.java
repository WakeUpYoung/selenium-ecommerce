package cn.wakeupeidolon.service;


import cn.wakeupeidolon.bean.Commodity;

import java.util.List;

/**
 * @author Wang Yu
 */
public interface CommodityService {
    Commodity save(Commodity comment);
    
    Integer batchSave(List<Commodity> commodityList);
}

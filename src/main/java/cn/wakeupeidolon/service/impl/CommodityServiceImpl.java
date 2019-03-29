package cn.wakeupeidolon.service.impl;

import cn.wakeupeidolon.bean.Commodity;
import cn.wakeupeidolon.dao.CommodityDao;
import cn.wakeupeidolon.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wang Yu
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    
    private final CommodityDao commodityDao;
    
    @Autowired
    public CommodityServiceImpl(CommodityDao commodityDao) {
        this.commodityDao = commodityDao;
    }
    
    @Override
    public Commodity save(Commodity comment) {
        return commodityDao.save(comment);
    }
    
    @Override
    public Integer batchSave(List<Commodity> commodityList) {
        List<Commodity> commodities = commodityDao.saveAll(commodityList);
        return commodities.size();
    }
    
    @Override
    public Boolean hasRepeat(String itemId, int type) {
        Integer count = commodityDao.countByItemIdAndType(itemId, type);
        return count > 0;
    }
}

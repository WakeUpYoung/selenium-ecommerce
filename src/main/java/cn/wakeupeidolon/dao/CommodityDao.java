package cn.wakeupeidolon.dao;

import cn.wakeupeidolon.bean.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wang Yu
 */
@Repository
public interface CommodityDao extends JpaRepository<Commodity, Long> {
}

package com.rongzer.demo.statemachine.dao;

import com.rongzer.demo.statemachine.model.MaintainOrder;
import com.rongzer.demo.statemachine.model.PollingCheckOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MaintainDao {

    private static Map<String, MaintainOrder> maintainOrderCacheMap = new HashMap<>();

    public List<MaintainOrder> getAll() {
        return new ArrayList<>(maintainOrderCacheMap.values());
    }

    public MaintainOrder getByNo(String orderNo) {
        MaintainOrder order = maintainOrderCacheMap.get(orderNo);
        return order == null ? new MaintainOrder(orderNo) : order;
    }

    public boolean save(MaintainOrder order) {
        maintainOrderCacheMap.put(order.getOrderNo(), order);
        return true;
    }


}

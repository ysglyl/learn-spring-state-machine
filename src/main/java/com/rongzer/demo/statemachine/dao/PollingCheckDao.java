package com.rongzer.demo.statemachine.dao;

import com.rongzer.demo.statemachine.model.PollingCheckOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PollingCheckDao {

    private static Map<String, PollingCheckOrder> pollingCheckOrderCacheMap = new HashMap<>();

    public List<PollingCheckOrder> getAll() {
        return new ArrayList<PollingCheckOrder>(pollingCheckOrderCacheMap.values());
    }

    public PollingCheckOrder getByNo(String orderNo) {
        PollingCheckOrder order = pollingCheckOrderCacheMap.get(orderNo);
        return order == null ? new PollingCheckOrder(orderNo) : order;
    }

    public boolean save(PollingCheckOrder order) {
        pollingCheckOrderCacheMap.put(order.getOrderNo(), order);
        return true;
    }


}

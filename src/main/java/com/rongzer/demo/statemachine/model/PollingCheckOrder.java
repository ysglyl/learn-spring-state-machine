package com.rongzer.demo.statemachine.model;

import com.rongzer.demo.statemachine.state.PollingCheckStates;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PollingCheckOrder {

    private String orderNo;
    private PollingCheckStates orderState;

    public PollingCheckOrder(String orderNo) {
        this.orderNo = orderNo;
    }

    public PollingCheckOrder(String orderNo, PollingCheckStates state) {
        this.orderNo = orderNo;
        this.orderState = state;
    }



}

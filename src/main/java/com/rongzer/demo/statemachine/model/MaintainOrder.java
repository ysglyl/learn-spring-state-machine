package com.rongzer.demo.statemachine.model;

import com.rongzer.demo.statemachine.state.MaintainStates;
import com.rongzer.demo.statemachine.state.PollingCheckStates;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MaintainOrder {

    private String orderNo;
    private MaintainStates orderState;

    public MaintainOrder(String orderNo) {
        this.orderNo = orderNo;
    }

    public MaintainOrder(String orderNo, MaintainStates state) {
        this.orderNo = orderNo;
        this.orderState = state;
    }



}

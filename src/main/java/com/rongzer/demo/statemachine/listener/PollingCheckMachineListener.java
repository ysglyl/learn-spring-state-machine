package com.rongzer.demo.statemachine.listener;

import com.rongzer.demo.statemachine.dao.PollingCheckDao;
import com.rongzer.demo.statemachine.event.PollingCheckEvents;
import com.rongzer.demo.statemachine.model.PollingCheckOrder;
import com.rongzer.demo.statemachine.state.PollingCheckStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(name = "pollingCheckStateMachineFactory")
public class PollingCheckMachineListener {

    @Autowired
    private PollingCheckDao pollingCheckDao;

    @OnTransition(source = "INIT", target = "PLAN")
    public void plan(Message<PollingCheckEvents> message) {
        System.out.println("plan");
        PollingCheckOrder order = message.getHeaders().get("order", PollingCheckOrder.class);
        order.setOrderState(PollingCheckStates.PLAN);
        pollingCheckDao.save(order);
    }

    @OnTransition(source = "PLAN", target = "RUNNING")
    public void running(Message<PollingCheckEvents> message) {
        System.out.println("running");
        PollingCheckOrder order = message.getHeaders().get("order", PollingCheckOrder.class);
        order.setOrderState(PollingCheckStates.RUNNING);
        pollingCheckDao.save(order);
    }

    @OnTransition(source = "RUNNING", target = "FINISH")
    public void finish(Message<PollingCheckEvents> message) {
        System.out.println("finish");
        PollingCheckOrder order = message.getHeaders().get("order", PollingCheckOrder.class);
        order.setOrderState(PollingCheckStates.FINISH);
        pollingCheckDao.save(order);
    }

}

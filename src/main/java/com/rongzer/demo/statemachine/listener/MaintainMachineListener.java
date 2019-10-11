package com.rongzer.demo.statemachine.listener;

import com.rongzer.demo.statemachine.dao.MaintainDao;
import com.rongzer.demo.statemachine.event.MaintainEvents;
import com.rongzer.demo.statemachine.model.MaintainOrder;
import com.rongzer.demo.statemachine.state.MaintainStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(name = "maintainStateMachineFactory")
public class MaintainMachineListener {

    @Autowired
    private MaintainDao maintainDao;

    @OnTransition(source = "INIT", target = "PLAN")
    public void plan(Message<MaintainEvents> message) {
        System.out.println("plan");
        MaintainOrder order = message.getHeaders().get("order", MaintainOrder.class);
        order.setOrderState(MaintainStates.PLAN);
        maintainDao.save(order);
    }

    @OnTransition(source = "PLAN", target = "RUNNING")
    public void running(Message<MaintainEvents> message) {
        System.out.println("running");
        MaintainOrder order = message.getHeaders().get("order", MaintainOrder.class);
        order.setOrderState(MaintainStates.RUNNING);
        maintainDao.save(order);
    }

    @OnTransition(source = "RUNNING", target = "FINISH")
    public void finish(Message<MaintainEvents> message) {
        System.out.println("finish");
        MaintainOrder order = message.getHeaders().get("order", MaintainOrder.class);
        order.setOrderState(MaintainStates.FINISH);
        maintainDao.save(order);
    }

}

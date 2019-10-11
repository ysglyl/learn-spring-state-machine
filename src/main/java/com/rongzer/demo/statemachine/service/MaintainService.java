package com.rongzer.demo.statemachine.service;

import com.rongzer.demo.statemachine.dao.MaintainDao;
import com.rongzer.demo.statemachine.event.MaintainEvents;
import com.rongzer.demo.statemachine.model.MaintainOrder;
import com.rongzer.demo.statemachine.state.MaintainStates;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class MaintainService {

    @Resource
    private StateMachineFactory<MaintainStates, MaintainEvents> maintainStateMachineFactory;

    @Resource
    private StateMachinePersister<MaintainStates, MaintainEvents, MaintainOrder> maintainStateMachinePersistēr;

    @Resource
    private MaintainDao MaintainDao;

    public List<MaintainOrder> list() {
        return MaintainDao.getAll();
    }

    public String start() {
        String orderNo = UUID.randomUUID().toString();
        MaintainDao.save(new MaintainOrder(orderNo, MaintainStates.INIT));
        return orderNo;
    }

    public boolean plan(String orderNo) {
        MaintainOrder order = MaintainDao.getByNo(orderNo);
        StateMachine<MaintainStates, MaintainEvents> stateMachine = getStateMachine(order);
        Message<MaintainEvents> message = MessageBuilder.withPayload(MaintainEvents.PLAN).setHeader("order", order).build();
        stateMachine.sendEvent(message);
        return true;
    }

    public boolean notice(String orderNo) {
        MaintainOrder order = MaintainDao.getByNo(orderNo);
        StateMachine<MaintainStates, MaintainEvents> stateMachine = getStateMachine(order);
        Message<MaintainEvents> message = MessageBuilder.withPayload(MaintainEvents.NOTICE).setHeader("order", order).build();
        stateMachine.sendEvent(message);
        return true;
    }

    public boolean work(String orderNo) {
        MaintainOrder order = MaintainDao.getByNo(orderNo);
        StateMachine<MaintainStates, MaintainEvents> stateMachine = getStateMachine(order);
        Message<MaintainEvents> message = MessageBuilder.withPayload(MaintainEvents.WORK).setHeader("order", order).build();
        stateMachine.sendEvent(message);
        return true;
    }

    /**
     * 获取状态机
     *
     * @param order
     * @return
     */
    private StateMachine<MaintainStates, MaintainEvents> getStateMachine(MaintainOrder order) {
        StateMachine<MaintainStates, MaintainEvents> stateMachine = maintainStateMachineFactory.getStateMachine();
        try {
            maintainStateMachinePersistēr.restore(stateMachine, order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stateMachine;
    }

}

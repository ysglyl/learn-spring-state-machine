package com.rongzer.demo.statemachine.service;

import com.rongzer.demo.statemachine.dao.PollingCheckDao;
import com.rongzer.demo.statemachine.event.PollingCheckEvents;
import com.rongzer.demo.statemachine.model.PollingCheckOrder;
import com.rongzer.demo.statemachine.state.PollingCheckStates;
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
public class PollingCheckService {

    @Resource
    private StateMachineFactory<PollingCheckStates, PollingCheckEvents> pollingCheckStateMachineFactory;

    @Resource
    private StateMachinePersister<PollingCheckStates, PollingCheckEvents, PollingCheckOrder> pollingCheckStateMachinePersistēr;

    @Resource
    private PollingCheckDao pollingCheckDao;

    public List<PollingCheckOrder> list() {
        return pollingCheckDao.getAll();
    }

    public String start() {
        String orderNo = UUID.randomUUID().toString();
        pollingCheckDao.save(new PollingCheckOrder(orderNo, PollingCheckStates.INIT));
        return orderNo;
    }

    public boolean plan(String orderNo) {
        PollingCheckOrder order = pollingCheckDao.getByNo(orderNo);
        StateMachine<PollingCheckStates, PollingCheckEvents> stateMachine = getStateMachine(order);
        Message<PollingCheckEvents> message = MessageBuilder.withPayload(PollingCheckEvents.PLAN).setHeader("order", order).build();
        stateMachine.sendEvent(message);
        return true;
    }

    public boolean notice(String orderNo) {
        PollingCheckOrder order = pollingCheckDao.getByNo(orderNo);
        StateMachine<PollingCheckStates, PollingCheckEvents> stateMachine = getStateMachine(order);
        Message<PollingCheckEvents> message = MessageBuilder.withPayload(PollingCheckEvents.NOTICE).setHeader("order", order).build();
        stateMachine.sendEvent(message);
        return true;
    }

    public boolean work(String orderNo) {
        PollingCheckOrder order = pollingCheckDao.getByNo(orderNo);
        StateMachine<PollingCheckStates, PollingCheckEvents> stateMachine = getStateMachine(order);
        Message<PollingCheckEvents> message = MessageBuilder.withPayload(PollingCheckEvents.WORK).setHeader("order", order).build();
        stateMachine.sendEvent(message);
        return true;
    }

    /**
     * 获取状态机
     *
     * @param order
     * @return
     */
    private StateMachine<PollingCheckStates, PollingCheckEvents> getStateMachine(PollingCheckOrder order) {
        StateMachine<PollingCheckStates, PollingCheckEvents> stateMachine = pollingCheckStateMachineFactory.getStateMachine();
        try {
            pollingCheckStateMachinePersistēr.restore(stateMachine, order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stateMachine;
    }

}

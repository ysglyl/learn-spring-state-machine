package com.rongzer.demo.statemachine.configuraton;

import com.rongzer.demo.statemachine.event.MaintainEvents;
import com.rongzer.demo.statemachine.event.PollingCheckEvents;
import com.rongzer.demo.statemachine.model.MaintainOrder;
import com.rongzer.demo.statemachine.model.PollingCheckOrder;
import com.rongzer.demo.statemachine.state.MaintainStates;
import com.rongzer.demo.statemachine.state.PollingCheckStates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.action.StateDoActionPolicy;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "maintainStateMachineFactory")
public class MaintainStateMachineConfig extends EnumStateMachineConfigurerAdapter<MaintainStates, MaintainEvents> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<MaintainStates, MaintainEvents> config) throws Exception {
        config.withConfiguration()
                .stateDoActionPolicy(StateDoActionPolicy.IMMEDIATE_CANCEL);
    }

    /**
     * 配置初始化状态和可能的状态
     *
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<MaintainStates, MaintainEvents> states) throws Exception {
        states.withStates().initial(MaintainStates.INIT).states(EnumSet.allOf(MaintainStates.class));
    }

    /**
     * 配置状态迁移动作
     *
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<MaintainStates, MaintainEvents> transitions) throws Exception {
        transitions
                .withExternal().source(MaintainStates.INIT).target(MaintainStates.PLAN).event(MaintainEvents.PLAN)
                .and()
                .withExternal().source(MaintainStates.PLAN).target(MaintainStates.RUNNING).event(MaintainEvents.NOTICE)
                .and()
                .withExternal().source(MaintainStates.RUNNING).target(MaintainStates.FINISH).event(MaintainEvents.WORK);

    }

    @Bean("maintainStateMachinePersistēr")
    public StateMachinePersister<MaintainStates, MaintainEvents, MaintainOrder> maintainStateMachinePersistēr() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<MaintainStates, MaintainEvents, MaintainOrder>() {
            @Override
            public void write(StateMachineContext<MaintainStates, MaintainEvents> context, MaintainOrder contextObj) throws Exception {
            }

            @Override
            public StateMachineContext<MaintainStates, MaintainEvents> read(MaintainOrder order) throws Exception {
                return new DefaultStateMachineContext<>(order.getOrderState(), null, null, null, null, "maintainStateMachineFactory");
            }
        });
    }

}

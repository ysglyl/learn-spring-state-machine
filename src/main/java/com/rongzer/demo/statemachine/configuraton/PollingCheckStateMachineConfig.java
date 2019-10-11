package com.rongzer.demo.statemachine.configuraton;

import com.rongzer.demo.statemachine.event.PollingCheckEvents;
import com.rongzer.demo.statemachine.model.PollingCheckOrder;
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
@EnableStateMachineFactory(name = "pollingCheckStateMachineFactory")
public class PollingCheckStateMachineConfig extends EnumStateMachineConfigurerAdapter<PollingCheckStates, PollingCheckEvents> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<PollingCheckStates, PollingCheckEvents> config) throws Exception {
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
    public void configure(StateMachineStateConfigurer<PollingCheckStates, PollingCheckEvents> states) throws Exception {
        states.withStates().initial(PollingCheckStates.INIT).states(EnumSet.allOf(PollingCheckStates.class));
    }

    /**
     * 配置状态迁移动作
     *
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<PollingCheckStates, PollingCheckEvents> transitions) throws Exception {
        transitions
                .withExternal().source(PollingCheckStates.INIT).target(PollingCheckStates.PLAN).event(PollingCheckEvents.PLAN)
                .and()
                .withExternal().source(PollingCheckStates.PLAN).target(PollingCheckStates.RUNNING).event(PollingCheckEvents.NOTICE)
                .and()
                .withExternal().source(PollingCheckStates.RUNNING).target(PollingCheckStates.FINISH).event(PollingCheckEvents.WORK);

    }

    @Bean("pollingCheckStateMachinePersistēr")
    public StateMachinePersister<PollingCheckStates, PollingCheckEvents, PollingCheckOrder> pollingCheckStateMachinePersistēr() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<PollingCheckStates, PollingCheckEvents, PollingCheckOrder>() {
            @Override
            public void write(StateMachineContext<PollingCheckStates, PollingCheckEvents> context, PollingCheckOrder contextObj) throws Exception {
            }

            @Override
            public StateMachineContext<PollingCheckStates, PollingCheckEvents> read(PollingCheckOrder order) throws Exception {
                return new DefaultStateMachineContext<>(order.getOrderState(), null, null, null, null, "pollingCheckStateMachineFactory");
            }
        });
    }

}

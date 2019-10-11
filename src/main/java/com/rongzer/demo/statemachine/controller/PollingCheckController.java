package com.rongzer.demo.statemachine.controller;

import com.rongzer.demo.statemachine.service.PollingCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pollingCheck")
public class PollingCheckController extends BaseController {

    @Autowired
    private PollingCheckService pollingCheckService;

    @RequestMapping("/list")
    public String list() {
        return getSuccess(pollingCheckService.list());
    }

    @RequestMapping("/start")
    public String start() {
        return getSuccess(pollingCheckService.start());
    }

    @RequestMapping("/plan/{orderNo}")
    public String plan(@PathVariable("orderNo") String orderNo) {
        pollingCheckService.plan(orderNo);
        return getSuccess();
    }

    @RequestMapping("/notice/{orderNo}")
    public String notice(@PathVariable("orderNo") String orderNo) {
        pollingCheckService.notice(orderNo);
        return getSuccess();
    }

    @RequestMapping("/work/{orderNo}")
    public String work(@PathVariable("orderNo") String orderNo) {
        pollingCheckService.work(orderNo);
        return getSuccess();
    }

}

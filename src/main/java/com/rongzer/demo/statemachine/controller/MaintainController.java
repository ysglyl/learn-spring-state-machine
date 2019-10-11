package com.rongzer.demo.statemachine.controller;

import com.rongzer.demo.statemachine.service.MaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maintain")
public class MaintainController extends BaseController {

    @Autowired
    private MaintainService maintainService;

    @RequestMapping("/list")
    public String list() {
        return getSuccess(maintainService.list());
    }

    @RequestMapping("/start")
    public String start() {
        return getSuccess(maintainService.start());
    }

    @RequestMapping("/plan/{orderNo}")
    public String plan(@PathVariable("orderNo") String orderNo) {
        maintainService.plan(orderNo);
        return getSuccess();
    }

    @RequestMapping("/notice/{orderNo}")
    public String notice(@PathVariable("orderNo") String orderNo) {
        maintainService.notice(orderNo);
        return getSuccess();
    }

    @RequestMapping("/work/{orderNo}")
    public String work(@PathVariable("orderNo") String orderNo) {
        maintainService.work(orderNo);
        return getSuccess();
    }

}

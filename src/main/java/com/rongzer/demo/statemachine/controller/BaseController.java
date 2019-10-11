package com.rongzer.demo.statemachine.controller;

import com.alibaba.fastjson.JSONObject;

public class BaseController {

    protected String getSuccess() {
        return getSuccess(null);
    }

    protected String getSuccess(Object data) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        if (data != null) {
            json.put("data", data);
        }
        return json.toJSONString();
    }

}

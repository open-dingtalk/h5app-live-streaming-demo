package com.dingtalk.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.handler.EventHandler;
import org.springframework.stereotype.Service;

/**
 * 回调测试url，自行处理
 */
@Service
public class CheckUrlEventHandler implements EventHandler {

    @Override
    public void handler(JSONObject eventJson) {

    }
}

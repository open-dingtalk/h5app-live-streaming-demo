package com.dingtalk.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.config.AppConfig;
import com.dingtalk.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 推送suiteTicket
 */
@Service
@Slf4j(topic = "suite_ticket_log")
public class SuiteTicketEventHandler implements EventHandler {

    /**
     * 接收到推送过来的suiteTicket时保存到application.properties(target里面)
     * @param eventJson
     */
    @Override
    public void handler(JSONObject eventJson) {
        log.info("eventJson: {}", eventJson.toJSONString() );
        String suiteTicket = eventJson.getString("suiteTicket");
//        AppConfig.setSuiteTicket(suiteTicket);
    }

}

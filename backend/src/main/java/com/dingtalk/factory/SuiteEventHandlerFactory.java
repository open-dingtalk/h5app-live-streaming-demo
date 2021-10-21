package com.dingtalk.factory;


import com.dingtalk.handler.EventHandler;
import com.dingtalk.handler.impl.SuiteAuthCodeEventHandler;
import com.dingtalk.handler.impl.SuiteTicketEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 回调测试事件工厂
 */
@Component
public class SuiteEventHandlerFactory extends AbstractEventHandlerFactory {

    @Autowired
    private SuiteTicketEventHandler suiteTicketEventHandler;
    @Autowired
    private SuiteAuthCodeEventHandler suiteAuthCodeEventHandler;


    @Override
    public EventHandler getEventHandler(String eventType) {
        if ("suite_ticket".equalsIgnoreCase(eventType)) {
            return suiteTicketEventHandler;
        } else if ("tmp_auth_code".equals(eventType) || "org_suite_auth".equals(eventType)) {
            // http推送方式授权应用
            return suiteAuthCodeEventHandler;
        }  else {
            throw new RuntimeException("tmp eventType not match！");
        }
    }
}

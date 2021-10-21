package com.dingtalk.factory;

import com.dingtalk.handler.EventHandler;
import com.dingtalk.handler.impl.CheckUrlEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 回调测试事件工厂
 */
@Component
public class CheckUrlEventHandlerFactory extends AbstractEventHandlerFactory {

    @Autowired
    private CheckUrlEventHandler checkUrlEventHandler;

    @Override
    public EventHandler getEventHandler(String eventType) {
        return checkUrlEventHandler;
    }
}

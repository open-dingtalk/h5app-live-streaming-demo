package com.dingtalk.factory;


import com.dingtalk.handler.EventHandler;

/**
 * 事件处理抽象工厂
 */
public abstract class AbstractEventHandlerFactory {

    public abstract EventHandler getEventHandler(String eventType);

}

package com.dingtalk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.config.AppConfig;
import com.dingtalk.factory.EventHandlerFactoryProducer;
import com.dingtalk.util.DingCallbackCrypto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Map;

/**
 * ISV 小程序回调信息处理
 */
@RestController
@RequestMapping("/")
public class CallbackController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventHandlerFactoryProducer eventHandlerFactoryProducer;


    @PostMapping(value = "dingCallback")
    public Object dingCallback(
        @RequestParam(value = "signature") String signature,
        @RequestParam(value = "timestamp") String timestamp,
        @RequestParam(value = "nonce") String nonce,
        @RequestBody(required = false) JSONObject json
    ) {
        String params = "signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
        log.info("begin callback:" + params);
        try {
            // 1.使用加解密类型
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(AppConfig.getToken(), AppConfig.getEncodingAesKey(), AppConfig.getSuiteKey());
            String encryptMsg = json.getString("encrypt");
            String decryptMsg = callbackCrypto.getDecryptMsg(signature, timestamp, nonce, encryptMsg);

            // 2. 反序列化回调事件json数据
            JSONObject eventJson = JSON.parseObject(decryptMsg);
            log.info("eventJson: {}", eventJson);
            String eventType = eventJson.getString("EventType");
            // 3. 根据EventType分类处理
            if ("SYNC_HTTP_PUSH_HIGH".equalsIgnoreCase(eventType) || "SYNC_HTTP_PUSH_MEDIUM".equalsIgnoreCase(eventType)) {
                JSONArray bizData = eventJson.getJSONArray("bizData");
                for (Object bizDatum : bizData) {
                    JSONObject jsonObject = (JSONObject) bizDatum;
                    JSONObject data = JSONObject.parseObject(jsonObject.getString("biz_data"));
                    eventType = data.getString("syncAction");
                    eventHandlerFactoryProducer.getEventHandlerFactory(eventType).getEventHandler(eventType).handler(data);
                }
            } else {
                eventHandlerFactoryProducer.getEventHandlerFactory(eventType).getEventHandler(eventType).handler(eventJson);
            }

            // 4. 返回success的加密数据
            return callbackCrypto.getEncryptedMap("success");

        } catch (DingCallbackCrypto.DingTalkEncryptException e) {
            e.printStackTrace();
            log.error("process callback failed！msg: {}", JSON.toJSONString(e));
            throw new RuntimeException("process callback failed！");
        }
    }
}

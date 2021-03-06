package com.dingtalk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static String suiteTicket;

    private static String suiteKey;

    private static String suiteSecret;

    private static String appId;

    private static String token;

    private static String encodingAesKey;

    private static String groupTemplateId;

    public static String getAppId() {
        return appId;
    }

    @Value("${app.app-id}")
    public void setAppId(String appId) {
        AppConfig.appId = appId;
    }

    public static String getGroupTemplateId() {
        return groupTemplateId;
    }

    @Value("${app.group-template-id}")
    public void setGroupTemplateId(String groupTemplateId) {
        AppConfig.groupTemplateId = groupTemplateId;
    }

    public static String getSuiteTicket() {
        return suiteTicket;
    }

    public static void setSuiteTicketStatic(String suiteTicket) {
        AppConfig.suiteTicket = suiteTicket;
    }

    @Value("${app.suite-ticket}")
    public void setSuiteTicket(String suiteTicket) {
        AppConfig.suiteTicket = suiteTicket;
    }

    public static String getSuiteKey() {
        return suiteKey;
    }

    @Value("${app.suite-key}")
    public void setSuiteKey(String suiteKey) {
        AppConfig.suiteKey = suiteKey;
    }

    public static String getSuiteSecret() {
        return suiteSecret;
    }

    @Value("${app.suite-secret}")
    public void setSuiteSecret(String suiteSecret) {
        AppConfig.suiteSecret = suiteSecret;
    }

    public static String getToken() {
        return token;
    }

    @Value("${app.token}")
    public void setToken(String token) {
        AppConfig.token = token;
    }

    public static String getEncodingAesKey() {
        return encodingAesKey;
    }

    @Value("${app.encoding-aes-key}")
    public void setEncodingAesKey(String encodingAesKey) {
        AppConfig.encodingAesKey = encodingAesKey;
    }
}
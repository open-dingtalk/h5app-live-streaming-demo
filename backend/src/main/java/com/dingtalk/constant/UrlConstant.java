package com.dingtalk.constant;

/**
 * 钉钉开放接口网关常量
 */
public class UrlConstant {

    /**
     *获取用户在企业内userId的接口URL
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";
    /**
     * 获取授权码的接口 url
     */
    public static final String GET_PERMANENT_CODE_URL = "https://oapi.dingtalk.com/service/get_permanent_code?suite_access_token=SUITE_ACCESS_TOKEN";
    /**
     * 激活企业授权的应用 url
     */
    public static final String ACTIVATE_SUITE_URL = "https://oapi.dingtalk.com/service/activate_suite?suite_access_token=SUITE_ACCESS_TOKEN";

    /**
     * 根据用户id获取用户详情 url
     */
    public static final String USER_GET_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";
    /**
     * 获取可访问企业相关信息的accessToken的URL
     */
    public static final String URL_GET_CORP_TOKEN = "https://oapi.dingtalk.com/service/get_corp_token";
    /**
     * 获取可访问企业相关信息的accessToken的URL
     */
    public static final String SUITE_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/service/get_suite_token";
    /**
     * 创建培训课程的URL
     */
    public static final String FEEDS_CREATE_URL = "https://oapi.dingtalk.com/topapi/planetom/feeds/create";
    /**
     * 直播观看数据
     */
    public static final String WATCH_DATA_URL = "https://oapi.dingtalk.com/topapi/planetom/feeds/watchdata/get";
    /**
     * 创建群会话
     */
    public static final String CHAT_CREATE = "https://oapi.dingtalk.com/chat/create";

}

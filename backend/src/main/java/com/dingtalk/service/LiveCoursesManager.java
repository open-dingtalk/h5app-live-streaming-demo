package com.dingtalk.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkim_1_0.Client;
import com.aliyun.dingtalkim_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiImChatScenegroupCreateRequest;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.dingtalk.util.RandomUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author nannanness
 */
@Service
@Slf4j
public class LiveCoursesManager {

    /**
     * 创建课程（直播）
     *
     * @param
     * @return
     */
    public String createCourses(String userId, String title) throws ApiException {
        // 1. 获取access_token
//        String accessToken = AccessTokenUtil.getAccessToken();

        return null;
    }


    public Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }
}

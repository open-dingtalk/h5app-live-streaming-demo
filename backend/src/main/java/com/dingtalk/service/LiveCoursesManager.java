package com.dingtalk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkim_1_0.Client;
import com.aliyun.dingtalkim_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatCreateRequest;
import com.dingtalk.api.request.OapiImChatScenegroupCreateRequest;
import com.dingtalk.api.request.OapiPlanetomFeedsCreateRequest;
import com.dingtalk.api.request.OapiPlanetomFeedsWatchdataGetRequest;
import com.dingtalk.api.response.OapiChatCreateResponse;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsWatchdataGetResponse;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.dingtalk.util.RandomUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @author nannanness
 */
@Service
@Slf4j(topic = "live_course")
public class LiveCoursesManager {

    /**
     * 创建课程（直播）
     *
     * @param
     * @return
     */
    public String createCourses(String corpId, OapiPlanetomFeedsCreateRequest request) throws ApiException {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getCorpAccessToken(corpId);
        String appId = AppConfig.getAppId();
        Long openAppId = Long.parseLong(appId);
        System.out.println("params: " + JSON.toJSONString(request));
        System.out.println("appId: " + appId);
        System.out.println("openAppId: " + openAppId);
        request.setOpenAppId(Long.parseLong(AppConfig.getAppId()));
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.FEEDS_CREATE_URL);
        OapiPlanetomFeedsCreateResponse rsp = client.execute(request, accessToken);
        log.info("createCourses rsp:{}", JSON.toJSONString(rsp));
        if(rsp.getSuccess()){
            return rsp.getResult();
        }
        return null;
    }

    /**
     * 查询直播观看数据
     *
     * @param
     * @return
     */
    public OapiPlanetomFeedsWatchdataGetResponse.OpenFeedWatchDetailRspModel getWatchData(String corpId, String chatId, String feedId, String userId) throws ApiException {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getCorpAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.WATCH_DATA_URL);
        OapiPlanetomFeedsWatchdataGetRequest req = new OapiPlanetomFeedsWatchdataGetRequest();
        req.setChatId(chatId);
        req.setFeedId(feedId);
        req.setPageSize(10L);
        req.setIndex(0L);
        req.setAnchorId(userId);
        OapiPlanetomFeedsWatchdataGetResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        log.info("getWatchData rsp:{}", JSON.toJSONString(rsp));
        if(rsp.getSuccess()){
            return rsp.getResult();
        }
        return null;
    }

    /**
     * 创建群会话
     *
     * @param
     * @return
     */
    public String createGroup(String corpId, String userId, String title) throws ApiException {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getCorpAccessToken(corpId);

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.SCENE_GROUP_CREATE);
        OapiImChatScenegroupCreateRequest req = new OapiImChatScenegroupCreateRequest();
        req.setOwnerUserId(userId);
        req.setTitle(title);
        req.setTemplateId(AppConfig.getGroupTemplateId());
        OapiImChatScenegroupCreateResponse rsp = client.execute(req, accessToken);
        log.info("createGroup rsp:{}", JSON.toJSONString(rsp));
        if(rsp.getSuccess()){
            return rsp.getResult().getOpenConversationId();
        }
        return null;
    }

}

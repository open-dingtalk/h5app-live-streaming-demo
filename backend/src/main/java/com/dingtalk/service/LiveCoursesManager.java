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
import com.dingtalk.api.request.OapiChatCreateRequest;
import com.dingtalk.api.request.OapiImChatScenegroupCreateRequest;
import com.dingtalk.api.request.OapiPlanetomFeedsCreateRequest;
import com.dingtalk.api.request.OapiPlanetomFeedsWatchdataGetRequest;
import com.dingtalk.api.response.OapiChatCreateResponse;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsWatchdataGetResponse;
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
    public OapiChatCreateResponse createGroup(String corpId, String name, String userId) throws ApiException {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getCorpAccessToken(corpId);
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.CHAT_CREATE);
        OapiChatCreateRequest req = new OapiChatCreateRequest();
        req.setName(name);
        req.setOwner(userId);
        req.setUseridlist(Arrays.asList(userId));
        OapiChatCreateResponse rsp = client.execute(req, accessToken);
        log.info("createGroup rsp:{}", JSON.toJSONString(rsp));
        if(rsp.isSuccess()){
            return rsp;
        }
        return null;
    }

}

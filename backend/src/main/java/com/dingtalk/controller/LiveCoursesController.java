package com.dingtalk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiPlanetomFeedsCreateRequest;
import com.dingtalk.api.response.OapiChatCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsWatchdataGetResponse;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.LiveCoursesManager;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "live_log")
@RestController
@RequestMapping("/live")
public class LiveCoursesController {

    @Autowired
    LiveCoursesManager liveCoursesManager;


    @RequestMapping("/createGroup")
    public RpcServiceResult createGroup(@RequestBody String param) throws ApiException {
        log.info("createGroup: param:{}", param);
        JSONObject jsonObject = JSONObject.parseObject(param);
        String corpId = jsonObject.getString("corpId");
        String name = jsonObject.getString("name");
        String userId = jsonObject.getString("userId");
        OapiChatCreateResponse group = liveCoursesManager.createGroup(corpId, name, userId);
        if(group == null){
            return RpcServiceResult.getFailureResult("-1", "创建群会话失败");
        }
        log.info("createGroup: group:{}", JSON.toJSONString(group));
        return RpcServiceResult.getSuccessResult(group);
    }

    @RequestMapping("/createCourse")
    public RpcServiceResult createCourse(@RequestBody String param) throws ApiException {
        log.info("createGroup: param:{}", param);
        JSONObject jsonObject = JSONObject.parseObject(param);
        String corpId = jsonObject.getString("corpId");
        OapiPlanetomFeedsCreateRequest request = JSONObject.parseObject(jsonObject.getString("params"), OapiPlanetomFeedsCreateRequest.class);
        String courseId = liveCoursesManager.createCourses(corpId, request);
        if(StringUtils.isEmpty(courseId)){
            return RpcServiceResult.getFailureResult("-1", "创建课程失败");
        }
        log.info("createCourse: courseId:{}", courseId);
        return RpcServiceResult.getSuccessResult(courseId);
    }

    @RequestMapping("/getWatchData")
    public RpcServiceResult getWatchData(@RequestBody String param) throws ApiException {
        log.info("createGroup: param:{}", param);
        JSONObject jsonObject = JSONObject.parseObject(param);
        String corpId = jsonObject.getString("corpId");
        String chatId = jsonObject.getString("chatId");
        String feedId = jsonObject.getString("feedId");
        String userId = jsonObject.getString("userId");
        OapiPlanetomFeedsWatchdataGetResponse.OpenFeedWatchDetailRspModel watchData = liveCoursesManager.getWatchData(corpId, chatId, feedId, userId);
        if(watchData == null){
            return RpcServiceResult.getFailureResult("-1", "查询直播数据失败");
        }
        log.info("getWatchData: watchData:{}", JSON.toJSONString(watchData));
        return RpcServiceResult.getSuccessResult(watchData);
    }

}

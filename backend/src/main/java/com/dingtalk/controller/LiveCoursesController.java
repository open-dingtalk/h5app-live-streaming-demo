package com.dingtalk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiPlanetomFeedsCreateRequest;
import com.dingtalk.api.response.OapiChatCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsCreateResponse;
import com.dingtalk.api.response.OapiPlanetomFeedsWatchdataGetResponse;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.LiveCoursesManager;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        String groupId = liveCoursesManager.createGroup(corpId, userId, name);
        if(StringUtils.isEmpty(groupId)){
            return RpcServiceResult.getFailureResult("-1", "创建场景群失败");
        }
        log.info("createGroup: groupId:{}", JSON.toJSONString(groupId));
        return RpcServiceResult.getSuccessResult(groupId);
    }

    @RequestMapping("/createCourse")
    public RpcServiceResult createCourse(@RequestBody String param) throws ApiException {
        log.info("createGroup: param:{}", param);
        JSONObject jsonObject = JSONObject.parseObject(param);
        String corpId = jsonObject.getString("corpId");
        OapiPlanetomFeedsCreateRequest request = JSONObject.parseObject(param, OapiPlanetomFeedsCreateRequest.class);
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

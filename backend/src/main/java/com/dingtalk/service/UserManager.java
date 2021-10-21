package com.dingtalk.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 */
@Service
public class UserManager {

    /**
     * 通过钉钉服务端API获取用户在当前企业的userId
     *
     * @param code        免登code
     */
    public String getUserId(String code, String corpId) {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getCorpAccessToken(corpId);

        // 2. 获取userid
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");

        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        if (response == null || !response.isSuccess()) {
            return null;
        }
        return response.getUserid();
    }

    /**
     * 根据用户id获取用户名称
     *
     * @param userId 用户id
     * @return
     */
    public String getUserName(String userId, String corpId) throws ApiException {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getCorpAccessToken(corpId);

        // 2. 获取用户详情
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.USER_GET_URL);
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid(userId);
        req.setLanguage("zh_CN");
        OapiV2UserGetResponse rsp = client.execute(req, accessToken);
        // 3. 返回用户名称
        return rsp.getResult().getName();
    }
}

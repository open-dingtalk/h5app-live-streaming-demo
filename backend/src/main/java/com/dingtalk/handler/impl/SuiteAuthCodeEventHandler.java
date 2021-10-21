package com.dingtalk.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiServiceActivateSuiteRequest;
import com.dingtalk.api.request.OapiServiceGetPermanentCodeRequest;
import com.dingtalk.api.response.OapiServiceActivateSuiteResponse;
import com.dingtalk.api.response.OapiServiceGetPermanentCodeResponse;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.exception.InvokeDingTalkException;
import com.dingtalk.handler.EventHandler;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业授权
 */
@Service
@Slf4j(topic = "auth_log")
public class SuiteAuthCodeEventHandler implements EventHandler {

    /**
     * 授权企业应用
     *
     * @param eventJson
     */
    @Override
    public void handler(JSONObject eventJson) {
        log.info("eventJson: {}", eventJson.toJSONString() );
        String suiteAccessToken = AccessTokenUtil.getSuiteAccessToken();

        String authCode = eventJson.getString("authCode");
        String permanentCode;
        String corpId;
        if (StringUtils.isNotBlank(authCode)) {
            // http推送授权
            OapiServiceGetPermanentCodeResponse permanentCodeResponse = getPermanentCode(authCode, suiteAccessToken);
            permanentCode =  permanentCodeResponse.getPermanentCode();
            corpId = permanentCodeResponse.getAuthCorpInfo().getCorpid();
        } else {
            // sync-http推送授权
            permanentCode = eventJson.getString("permanent_code");
            JSONObject authCorpInfo = JSONObject.parseObject(eventJson.getString("auth_corp_info"));
            corpId = authCorpInfo.getString("corpid");
        }
        activateSuite(permanentCode, corpId, suiteAccessToken);
    }

    /**
     * 激活应用
     * @param permanentCode
     * @param suiteAccessToken
     */
    private void activateSuite(String permanentCode, String corpId, String suiteAccessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient(UrlConstant.ACTIVATE_SUITE_URL.replace("SUITE_ACCESS_TOKEN", suiteAccessToken));
            OapiServiceActivateSuiteRequest req = new OapiServiceActivateSuiteRequest();
            req.setSuiteKey(AppConfig.getSuiteKey());
            req.setAuthCorpid(corpId);
            req.setPermanentCode(permanentCode);
            OapiServiceActivateSuiteResponse rsp = client.execute(req);
            if (!rsp.isSuccess()) {
                throw new InvokeDingTalkException(rsp.getErrorCode(), rsp.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new InvokeDingTalkException(e.getErrCode(), e.getErrMsg());
        }
    }

    /**
     * 获取授权企业的永久授权码
     * @param authCode
     * @param suiteAccessToken
     * @return
     * @throws ApiException
     */
    private OapiServiceGetPermanentCodeResponse getPermanentCode(String authCode, String suiteAccessToken) {
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_PERMANENT_CODE_URL.replace("SUITE_ACCESS_TOKEN", suiteAccessToken));
        OapiServiceGetPermanentCodeRequest req = new OapiServiceGetPermanentCodeRequest();
        req.setTmpAuthCode(authCode);
        try {
            OapiServiceGetPermanentCodeResponse rsp = client.execute(req);
            if (rsp.isSuccess()) {
                return rsp;
            } else {
                throw new InvokeDingTalkException(rsp.getErrorCode(), rsp.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new InvokeDingTalkException(e.getErrCode(), e.getErrMsg());
        }

    }

}

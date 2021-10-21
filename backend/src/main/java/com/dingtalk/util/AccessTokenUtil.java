package com.dingtalk.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.request.OapiServiceGetSuiteTokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.dingtalk.api.response.OapiServiceGetSuiteTokenResponse;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.exception.InvokeDingTalkException;
import com.taobao.api.ApiException;

/**
 * 获取access_token工具类
 */
public class AccessTokenUtil {

    /**
     * 授权企业的access_token
     * @param authCorpId
     * @return
     * @throws RuntimeException
     */
    public static String getCorpAccessToken(String authCorpId) throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.URL_GET_CORP_TOKEN);
            OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
            req.setAuthCorpid(authCorpId);
            OapiServiceGetCorpTokenResponse response = client.execute(req, AppConfig.getSuiteKey(), AppConfig.getSuiteSecret(), AppConfig.getSuiteTicket());
            if (response.isSuccess()) {
                return response.getAccessToken();

            } else {
                throw new InvokeDingTalkException(response.getErrorCode(), response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new InvokeDingTalkException(e.getErrCode(), e.getErrMsg());
        }

    }

    /**
     * 第三方企业token
     * @return
     * @throws RuntimeException
     */
    public static String getSuiteAccessToken() throws InvokeDingTalkException {
        try {
            DingTalkClient client = new DefaultDingTalkClient(UrlConstant.SUITE_ACCESS_TOKEN_URL);
            OapiServiceGetSuiteTokenRequest req = new OapiServiceGetSuiteTokenRequest();
            req.setSuiteKey(AppConfig.getSuiteKey());
            req.setSuiteSecret(AppConfig.getSuiteSecret());
            req.setSuiteTicket(AppConfig.getSuiteTicket());
            OapiServiceGetSuiteTokenResponse response = client.execute(req);
            if (response.isSuccess()) {
                String accessToken = response.getSuiteAccessToken();
                return accessToken;
            } else {
                throw new InvokeDingTalkException(response.getErrorCode(), response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new InvokeDingTalkException(e.getErrCode(), e.getErrMsg());
        }

    }
}

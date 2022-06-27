package com.zxw.enhancer;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class MyTokenEnhancer implements org.springframework.security.oauth2.provider.token.TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getPrincipal();

//        final Map<String, Object> additionalInfo = new HashMap<>();
//
//        final Map<String, Object> retMap = new HashMap<>();
//
//        //todo 这里暴露memberId到Jwt的令牌中,后期可以根据自己的业务需要 进行添加字段
//        additionalInfo.put("memberId",memberDetails.getUmsMember().getId());
//        additionalInfo.put("nickName",memberDetails.getUmsMember().getNickname());
//        additionalInfo.put("integration",memberDetails.getUmsMember().getIntegration());
//
//        retMap.put("additionalInfo",additionalInfo);
//
//        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(retMap);

        return accessToken;
    }
}

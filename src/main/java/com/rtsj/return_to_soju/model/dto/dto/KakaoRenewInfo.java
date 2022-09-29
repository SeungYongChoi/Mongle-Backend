package com.rtsj.return_to_soju.model.dto.dto;

import java.util.Map;

public class KakaoRenewInfo {
    protected Map<String, Object> attributes;

    public KakaoRenewInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes(){
        return attributes;
    }
    public String getAccessToken() {
        return attributes.get("access_token").toString();
    }

    public String getRefreshToken() {
        Object refresh_token = attributes.get("refresh_token");
        if(refresh_token == null){
            return null;
        }
        return attributes.get("refresh_token").toString();
    }

}

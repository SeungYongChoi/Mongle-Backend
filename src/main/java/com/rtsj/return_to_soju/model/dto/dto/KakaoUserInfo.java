package com.rtsj.return_to_soju.model.dto.dto;

import java.util.Map;

public class KakaoUserInfo{
    protected Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }
    public Map<String, Object> getAttributes(){
        return attributes;
    }
    public Long getId() {
        return Long.parseLong(String.valueOf(attributes.get("id")));
    }

    public String getName() {
        return (String) getKakaoAccount().get("name");
    }

    public String getNickName() {
        return (String) getProfile().get("nickname");
    }
    public Map<String, Object> getKakaoAccount(){
        return(Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String, Object> getProfile(){
        return (Map<String, Object>) getKakaoAccount().get("profile");
    }

    public String getProvider(){
        return "kakao";
    }
}
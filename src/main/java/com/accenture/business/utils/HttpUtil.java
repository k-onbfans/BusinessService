package com.accenture.business.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

public class HttpUtil<T> {
    @Autowired
    private RestTemplate restTemplate;

    public JSONObject postForEntity(String url, @Nullable T request){

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<T> req = new HttpEntity<T>(request, headers);

        ResponseEntity<JSONObject> entity = restTemplate.postForEntity(url, req, JSONObject.class);

        JSONObject result = entity.getBody();

        return result;
    }
}

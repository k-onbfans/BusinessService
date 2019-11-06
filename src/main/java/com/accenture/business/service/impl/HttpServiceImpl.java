package com.accenture.business.service.impl;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.service.HttpService;
import com.accenture.business.bean.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpServiceImpl implements HttpService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getFlightV1(String url){

        ResponseEntity<String> entity = restTemplate.getForEntity(url,String.class);

        return entity.getBody();
    }

    @Override
    public String postFlightV1(String url, FindByFlightNumberReq request) {

        ResponseEntity<String> entity = restTemplate.postForEntity(url,request,String.class);


        return entity.getBody();
    }

    @Override
    public String postFlightByRouteV1(String url, Port request) {

        ResponseEntity<String> entity = restTemplate.postForEntity(url,request,String.class);

        return entity.getBody();
    }
}

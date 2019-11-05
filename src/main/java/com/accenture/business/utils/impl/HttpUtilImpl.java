package com.accenture.business.utils.impl;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.accenture.business.response.FlightReses;
import com.accenture.business.utils.HttpUtil;
import com.accenture.business.v1.bean.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpUtilImpl implements HttpUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public FlightRes flightResFromFindByFlightNumberReq(String url, FindByFlightNumberReq request){

        ResponseEntity<FlightRes> entity = restTemplate.postForEntity(url,request,FlightRes.class);

        return entity.getBody();
    }

    @Override
    public FlightReses flightResesFromFindByRouteReq(String url, FindByRouteReq request){
        ResponseEntity<FlightReses> entity = restTemplate.postForEntity(url,request,FlightReses.class);

        return entity.getBody();
    }

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

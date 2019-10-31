package com.accenture.business.utils.impl;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.accenture.business.response.FlightReses;
import com.accenture.business.utils.HttpUtil;
import com.accenture.business.v1.bean.Port;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;
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
    public FlightV1Res GetFlightV1(String url){

        ResponseEntity<FlightV1Res> entity = restTemplate.getForEntity(url,FlightV1Res.class);

        return entity.getBody();
    }

    @Override
    public FlightV1Res PostFlightV1(String url, FindByFlightNumberReq request) {

        ResponseEntity<FlightV1Res> entity = restTemplate.postForEntity(url,request,FlightV1Res.class);

        return entity.getBody();
    }

    @Override
    public FlightV1Reses PostFlightByRouteV1(String url, Port request) {

        ResponseEntity<FlightV1Reses> entity = restTemplate.postForEntity(url,request,FlightV1Reses.class);

        return entity.getBody();
    }
}

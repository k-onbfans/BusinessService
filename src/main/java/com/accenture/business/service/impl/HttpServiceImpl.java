package com.accenture.business.service.impl;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;
import com.accenture.business.service.HttpService;
import com.accenture.business.bean.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpServiceImpl implements HttpService {

    @Value("${flightStatusByFDV1Url}")
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public FlightV1Res getFlightV1(String url){

        ResponseEntity<FlightV1Res> entity = restTemplate.getForEntity(url,FlightV1Res.class);

        return entity.getBody();
    }

    @Override
    public FlightV1Res postFlightTimeV1(FindByFlightNumberReq request) {

        ResponseEntity<FlightV1Res> entity = restTemplate.postForEntity(flightTimeUrl,request,FlightV1Res.class);


        return entity.getBody();
    }
    @Override
    public FlightV1Res postFlightStatusV1(FindByFlightNumberReq request) {

        ResponseEntity<FlightV1Res> entity = restTemplate.postForEntity(flightStatusUrl,request,FlightV1Res.class);


        return entity.getBody();
    }

    @Override
    public FlightV1Reses postFlightByRouteV1(Port request) {

        ResponseEntity<FlightV1Reses> entity = restTemplate.postForEntity(flightInfoByRouteUrl,request,FlightV1Reses.class);

        return entity.getBody();
    }
}

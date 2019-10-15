package com.accenture.business.controller;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {

    @RequestMapping("/findbyflightnumberanddeparturedate")
    public FlightRes findByFlightNumberAndDepartureDate(@RequestBody FindByFlightNumberReq request){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String flightInfoUrl = "http://localhost:8080/flight_info/findbyflightnumberanddeparturedate";

        HttpEntity<FindByFlightNumberReq> flightRequest = new HttpEntity<FindByFlightNumberReq>(request, headers);

        ResponseEntity<JSONObject> flightInfoEntity = restTemplate.postForEntity(flightInfoUrl, flightRequest, JSONObject.class);
        JSONObject result = flightInfoEntity.getBody();

        String flightStatusUrl = "http://localhost:8080/flight_status/findbyflightnumberanddeparturedate";

        ResponseEntity<JSONObject> flightStatusEntity = restTemplate.postForEntity(flightStatusUrl, flightRequest, JSONObject.class);
        result.putAll(flightStatusEntity.getBody());

        String flightTimeUrl = "http://localhost:8080/flight_time/findbyflightnumberanddeparturedate";

        ResponseEntity<JSONObject> flightTimeEntity = restTemplate.postForEntity(flightTimeUrl, flightRequest, JSONObject.class);
        result.putAll(flightTimeEntity.getBody());
        FlightRes flightRes  = new FlightRes(result);
        return flightRes;
    }

    @RequestMapping("/findbyflightnumberanddeparturedate2")
    public JSONObject findByFlightNumberAndDepartureDate2 (@RequestBody FindByFlightNumberReq request){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String flightInfoUrl = "http://localhost:8080/findbyflightnumberanddeparturedate";

        HttpEntity<FindByFlightNumberReq> flightRequest = new HttpEntity<FindByFlightNumberReq>(request, headers);

        ResponseEntity<JSONObject> flightInfoEntity = restTemplate.postForEntity(flightInfoUrl, flightRequest, JSONObject.class);
        JSONObject result = flightInfoEntity.getBody();

        return result;
    }

//    @RequestMapping("/findbyroute2")
//    public JSONObject findByRoute2 (@RequestBody FindByFlightNumberReq request){
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String flightInfoUrl = "http://localhost:8080/findbyflightnumberanddeparturedate";
//
//        HttpEntity<FindByFlightNumberReq> flightRequest = new HttpEntity<FindByFlightNumberReq>(request, headers);
//
//        ResponseEntity<JSONObject> flightInfoEntity = restTemplate.postForEntity(flightInfoUrl, flightRequest, JSONObject.class);
//        JSONObject result = flightInfoEntity.getBody();
//
//        return result;
//    }

    @RequestMapping("/findbyroute")
    public List<FlightRes> findByRoute (@RequestBody FindByRouteReq request){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String flightInfoUrl = "http://localhost:8080/flight_info/findbyoriginportanddestinationport";

        HttpEntity<FindByRouteReq> flightInfoRequest = new HttpEntity<FindByRouteReq>(request, headers);

        ResponseEntity<JSONObject> flightInfoEntity = restTemplate.postForEntity(flightInfoUrl, flightInfoRequest, JSONObject.class);
        JSONArray flightInfoResult = flightInfoEntity.getBody().getJSONArray("list");

        List<FlightRes> list = new ArrayList<FlightRes>();
        list= JSONObject.parseArray(flightInfoResult.toJSONString(), FlightRes.class);

        String flightStatusUrl = "http://localhost:8080/flight_status/findbyflightnumberanddeparturedate";
        String flightTimeUrl = "http://localhost:8080/flight_time/findbyflightnumberanddeparturedate";
        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        list.forEach(flightRes -> {
            req.setFlightNumber(flightRes.getFlightNumber());
            HttpEntity<FindByFlightNumberReq> flightStatusRequest = new HttpEntity<FindByFlightNumberReq>(req, headers);
            ResponseEntity<JSONObject> flightStatusEntity = restTemplate.postForEntity(flightStatusUrl, flightStatusRequest, JSONObject.class);
            JSONObject result = flightStatusEntity.getBody();
            FlightRes flightStatusRes = result.toJavaObject(FlightRes.class);
            /*youwenti*/
            BeanUtils.copyProperties(flightStatusRes,flightRes);
        });

        return list;
    }


}

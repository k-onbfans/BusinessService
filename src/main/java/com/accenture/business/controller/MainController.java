package com.accenture.business.controller;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    @Value("${flightInfoByFDUrl}")
    private String flightInfoByFDUrl;

    @Value("${flightStatusByFDUrl}")
    private String flightStatusByFDUrl;

    @Value("${flightTimeByFDUrl}")
    private String flightTimeByFDUrl;

    @Value("${flightByFDUrl}")
    private String flightByFDUrl;

    @Value("${flightInfoByODUrl}")
    private String flightInfoByODUrl;


    @RequestMapping("/findbyflightnumberanddeparturedate")
    public FlightRes findByFlightNumberAndDepartureDate(@RequestBody FindByFlightNumberReq request){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FindByFlightNumberReq> flightRequest = new HttpEntity<FindByFlightNumberReq>(request, headers);

        ResponseEntity<JSONObject> flightInfoEntity = restTemplate.postForEntity(flightInfoByFDUrl, flightRequest, JSONObject.class);
        JSONObject result = flightInfoEntity.getBody();

        ResponseEntity<JSONObject> flightStatusEntity = restTemplate.postForEntity(flightStatusByFDUrl, flightRequest, JSONObject.class);
        result.putAll(flightStatusEntity.getBody());

        ResponseEntity<JSONObject> flightTimeEntity = restTemplate.postForEntity(flightTimeByFDUrl, flightRequest, JSONObject.class);
        result.putAll(flightTimeEntity.getBody());
        FlightRes flightRes  = new FlightRes(result);
        return flightRes;
    }

    @RequestMapping("/findbyflightnumberanddeparturedate2")
    public JSONObject findByFlightNumberAndDepartureDate2 (@RequestBody FindByFlightNumberReq request){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FindByFlightNumberReq> flightRequest = new HttpEntity<FindByFlightNumberReq>(request, headers);

        ResponseEntity<JSONObject> flightInfoEntity = restTemplate.postForEntity(flightByFDUrl, flightRequest, JSONObject.class);
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

        HttpEntity<FindByRouteReq> flightInfoRequest = new HttpEntity<FindByRouteReq>(request, headers);

        ResponseEntity<JSONObject> flightInfoEntity = restTemplate.postForEntity(flightInfoByODUrl, flightInfoRequest, JSONObject.class);
        JSONArray flightInfoResult = flightInfoEntity.getBody().getJSONArray("list");

        List<FlightRes> list = new ArrayList<FlightRes>();
        list= JSONObject.parseArray(flightInfoResult.toJSONString(), FlightRes.class);

        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        list.forEach(flightRes -> {
            req.setFlightNumber(flightRes.getFlightNumber());
            HttpEntity<FindByFlightNumberReq> flightStatusRequest = new HttpEntity<FindByFlightNumberReq>(req, headers);
            ResponseEntity<JSONObject> flightStatusEntity = restTemplate.postForEntity(flightStatusByFDUrl, flightStatusRequest, JSONObject.class);
            JSONObject Statusresult = flightStatusEntity.getBody();
            FlightRes flightStatusRes = Statusresult.toJavaObject(FlightRes.class);
            flightRes.setStatus(flightStatusRes.getStatus());
            flightRes.setDepartureDate(flightStatusRes.getDepartureDate());

            req.setFlightNumber(flightRes.getFlightNumber());
            HttpEntity<FindByFlightNumberReq> flightTimeRequest = new HttpEntity<FindByFlightNumberReq>(req, headers);
            ResponseEntity<JSONObject> flightTimeEntity = restTemplate.postForEntity(flightTimeByFDUrl, flightTimeRequest, JSONObject.class);
            JSONObject Timeresult = flightTimeEntity.getBody();
            FlightRes flightTimeRes = Timeresult.toJavaObject(FlightRes.class);
            flightRes.setScheduledArrivalTime(flightTimeRes.getScheduledArrivalTime());
            flightRes.setScheduledDepartureTime(flightTimeRes.getScheduledDepartureTime());
            flightRes.setEstimatedArrivalTime(flightTimeRes.getEstimatedArrivalTime());
            flightRes.setEstimatedDepartureTime(flightTimeRes.getEstimatedDepartureTime());
        });

        return list;
    }


}

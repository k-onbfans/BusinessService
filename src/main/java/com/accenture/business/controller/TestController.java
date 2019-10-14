package com.accenture.business.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/testget")
    public List<JSONObject> test(){
        RestTemplate restTemplate = new RestTemplate();
        List<JSONObject> list= restTemplate.getForObject("http://localhost:8080/flight_info/findall", List.class);
        return list;
    }

    @RequestMapping("/testpost")
    public ResponseEntity<JSONObject> doPost(HttpServletResponse response, Integer userId) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url="http://localhost:8080/flight_info/findbyid";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> map = new HashMap<String, Object>();
        Integer id = 1;
        map.put("id", id);
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(map, headers);

        ResponseEntity<JSONObject> entity = restTemplate.postForEntity(url, request, JSONObject.class);

        return entity;
    }


}

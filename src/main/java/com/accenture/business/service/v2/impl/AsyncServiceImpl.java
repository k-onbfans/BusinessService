package com.accenture.business.service.v2.impl;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.service.HttpService;
import com.accenture.business.service.v2.AsyncService;
import com.accenture.business.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@EnableAsync
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private HttpService httpUtil;

    @Value("${flightStatusByFDV1Url}")
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;

    @Override
    @Async
    public Future<FlightV1Res> getStatusAndTime(FindByFlightNumberReq req, FlightV1Res res){
        req.setFlightNumber(res.getFlightNumber());

        String statusResponse = httpUtil.postFlightV1(flightStatusUrl,req);

        res = JSONUtil.stringToDTO(statusResponse,res,FlightV1Res.class);

        String timeResponse = httpUtil.postFlightV1(flightTimeUrl,req);

        JSONUtil.stringToDTO(timeResponse,res,FlightV1Res.class);
        return new AsyncResult<>(res);
    }
}

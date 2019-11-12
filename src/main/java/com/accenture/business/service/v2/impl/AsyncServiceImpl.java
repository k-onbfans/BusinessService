package com.accenture.business.service.v2.impl;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.service.HttpService;
import com.accenture.business.service.v2.AsyncService;
import org.springframework.beans.BeanUtils;
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

        FlightV1Res statusResponse = httpUtil.postFlightStatusV1(req);

        BeanUtils.copyProperties(statusResponse,res);

        FlightV1Res timeResponse = httpUtil.postFlightTimeV1(req);

        BeanUtils.copyProperties(timeResponse,res);

        return new AsyncResult<>(res);
    }
}

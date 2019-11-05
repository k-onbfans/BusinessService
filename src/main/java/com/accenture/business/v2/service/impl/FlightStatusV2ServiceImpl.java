package com.accenture.business.v2.service.impl;

import com.accenture.business.handler.aop.LogTime;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.utils.HttpUtil;
import com.accenture.business.utils.JSONUtil;
import com.accenture.business.v1.bean.Port;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;
import com.accenture.business.v2.service.FlightStatusV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
@EnableAsync
public class FlightStatusV2ServiceImpl implements FlightStatusV2Service {

    @Autowired
    private HttpUtil httpUtil;

    @Value("${flightStatusByFDV1Url}")
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;


    @Override
    @LogTime
    @Async
    public Future<FlightV1Reses> searchByRoute(FindByRouteReq request) {
        FlightV1Reses reses = new FlightV1Reses();
        Port port = new Port();
        port.setDestination(request.getDestinationPort());
        port.setOrigin(request.getOriginPort());
        String list = httpUtil.postFlightByRouteV1(flightInfoByRouteUrl,port);
        reses = JSONUtil.stringToDTO(list,reses,FlightV1Reses.class);
        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        List<FlightV1Res> resList = reses.getList();
        for(FlightV1Res res : resList){
            req.setFlightNumber(res.getFlightNumber());

            String statusResponse = httpUtil.postFlightV1(flightStatusUrl,req);

            res = JSONUtil.stringToDTO(statusResponse,res,FlightV1Res.class);

            String timeResponse = httpUtil.postFlightV1(flightTimeUrl,req);

            JSONUtil.stringToDTO(timeResponse,res,FlightV1Res.class);

        }
        reses.setList(resList);
        return new AsyncResult<>(reses);
    }
}

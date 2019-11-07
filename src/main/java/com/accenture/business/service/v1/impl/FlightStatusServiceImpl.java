package com.accenture.business.service.v1.impl;

import com.accenture.business.handler.aop.LogTime;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.service.HttpService;
import com.accenture.business.utils.JSONUtil;
import com.accenture.business.bean.Port;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;
import com.accenture.business.service.v1.FlightStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
public class FlightStatusServiceImpl implements FlightStatusService {

    @Autowired
    private HttpService httpService;

    @Value("${flightStatusByFDV1Url}")
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;

    @Override
    @LogTime
    public FlightV1Res searchByFlightNumber(FindByFlightNumberReq request) {

        String flightInfoUrl = "http://localhost:8080/v1/flightinfo/flightnumber/" + request.getFlightNumber();

        String infoResponse = httpService.getFlightV1(flightInfoUrl);
        FlightV1Res response = new FlightV1Res();
        response = JSONUtil.stringToDTO(infoResponse,response,FlightV1Res.class);

        String statusResponse = httpService.postFlightV1(flightStatusUrl,request);

        response = JSONUtil.stringToDTO(statusResponse,response,FlightV1Res.class);

        String timeResponse = httpService.postFlightV1(flightTimeUrl,request);

        response = JSONUtil.stringToDTO(timeResponse,response,FlightV1Res.class);

        return response;
    }

    @Override
    @LogTime
    @Cacheable(cacheNames="test_redis",key = "#request.departureDate")
    public FlightV1Reses searchByRoute(FindByRouteReq request) {
        FlightV1Reses reses = new FlightV1Reses();
        Port port = new Port();
        port.setDestination(request.getDestinationPort());
        port.setOrigin(request.getOriginPort());
        String list = httpService.postFlightByRouteV1(flightInfoByRouteUrl,port);
        reses = JSONUtil.stringToDTO(list,reses,FlightV1Reses.class);
        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        List<FlightV1Res> resList = reses.getList();
        for(FlightV1Res res : resList){
            req.setFlightNumber(res.getFlightNumber());

            String statusResponse = httpService.postFlightV1(flightStatusUrl,req);

            res = JSONUtil.stringToDTO(statusResponse,res,FlightV1Res.class);

            String timeResponse = httpService.postFlightV1(flightTimeUrl,req);

            JSONUtil.stringToDTO(timeResponse,res,FlightV1Res.class);

        }
        reses.setList(resList);
        return reses;
    }
}

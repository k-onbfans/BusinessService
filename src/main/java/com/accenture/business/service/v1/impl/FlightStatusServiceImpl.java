package com.accenture.business.service.v1.impl;

import com.accenture.business.handler.aop.LogTime;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.service.HttpService;
import com.accenture.business.bean.Port;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;
import com.accenture.business.service.v1.FlightStatusService;
import org.springframework.beans.BeanUtils;
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

    @Override
    @LogTime
    public FlightV1Res searchByFlightNumber(FindByFlightNumberReq request) {

        String flightInfoUrl = "http://localhost:8080/v1/flightinfo/flightnumber/" + request.getFlightNumber();

        FlightV1Res infoResponse = httpService.getFlightV1(flightInfoUrl);
        FlightV1Res response = new FlightV1Res();

        BeanUtils.copyProperties(infoResponse,response);

        FlightV1Res statusResponse = httpService.postFlightStatusV1(request);

        BeanUtils.copyProperties(statusResponse,response);

        FlightV1Res timeResponse = httpService.postFlightTimeV1(request);

        BeanUtils.copyProperties(timeResponse,response);


        return response;
    }

    @Override
    @LogTime
    @Cacheable
    public FlightV1Reses searchByRoute(FindByRouteReq request) {
        Port port = new Port();
        port.setDestination(request.getDestinationPort());
        port.setOrigin(request.getOriginPort());
        FlightV1Reses reses = httpService.postFlightByRouteV1(port);
        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        List<FlightV1Res> resList = reses.getList();
        for(FlightV1Res res : resList){
            req.setFlightNumber(res.getFlightNumber());

            FlightV1Res statusResponse = httpService.postFlightStatusV1(req);

            FlightV1Res timeResponse = httpService.postFlightTimeV1(req);

            BeanUtils.copyProperties(statusResponse,res);

            BeanUtils.copyProperties(timeResponse,res);

        }
        reses.setList(resList);
        return reses;
    }
}

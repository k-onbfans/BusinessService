package com.accenture.business.v1.service.impl;

import com.accenture.business.handler.aop.LogTime;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.utils.HttpUtil;
import com.accenture.business.v1.bean.Port;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;
import com.accenture.business.v1.service.FlightStatusService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightStatusServiceImpl implements FlightStatusService {

    @Autowired
    private HttpUtil httpUtil;

    @Value("${flightStatusByFDV1Url}")
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;

    public static final Gson GSON = new Gson();

    public static final JsonParser PARSER = new JsonParser();

    @Override
    @LogTime
    public FlightV1Res searchByFlightNumber(FindByFlightNumberReq request) {

        String FlightInfoUrl = "http://localhost:8080/v1/flightinfo/flightnumber/" + request.getFlightNumber();

        FlightV1Res infoResponse = httpUtil.GetFlightV1(FlightInfoUrl);

        FlightV1Res statusResponse = httpUtil.PostFlightV1(flightStatusUrl,request);

        FlightV1Res timeResponse = httpUtil.PostFlightV1(flightTimeUrl,request);

        infoResponse.setDepartureDate(statusResponse.getDepartureDate());
        infoResponse.setStatus(statusResponse.getStatus());
        infoResponse.setEstimatedTime(timeResponse.getEstimatedTime());
        infoResponse.setScheduledTime(timeResponse.getScheduledTime());

        return infoResponse;
    }

    @Override
    @LogTime
    public FlightV1Reses searchByRoute(FindByRouteReq request) {
        Port port = new Port();
        port.setDestination(request.getDestinationPort());
        port.setOrigin(request.getOriginPort());
        FlightV1Reses list = httpUtil.PostFlightByRouteV1(flightInfoByRouteUrl,port);
        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        for (FlightV1Res response: list.getList()) {
            req.setFlightNumber(response.getFlightNumber());
            FlightV1Res statusResponse = httpUtil.PostFlightV1(flightStatusUrl,req);

            FlightV1Res timeResponse = httpUtil.PostFlightV1(flightTimeUrl,req);

            response.setDepartureDate(statusResponse.getDepartureDate());
            response.setStatus(statusResponse.getStatus());
            response.setEstimatedTime(timeResponse.getEstimatedTime());
            response.setScheduledTime(timeResponse.getScheduledTime());
        }
        return list;
    }
}

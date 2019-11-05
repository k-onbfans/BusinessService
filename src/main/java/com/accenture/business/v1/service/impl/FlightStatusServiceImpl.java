package com.accenture.business.v1.service.impl;

import com.accenture.business.handler.aop.LogTime;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.utils.HttpUtil;
import com.accenture.business.utils.JSONUtil;
import com.accenture.business.v1.bean.Port;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;
import com.accenture.business.v1.service.FlightStatusService;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
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

        String flightInfoUrl = "http://localhost:8080/v1/flightinfo/flightnumber/" + request.getFlightNumber();

        String infoResponse = httpUtil.getFlightV1(flightInfoUrl);
        FlightV1Res response = new FlightV1Res();
        response = JSONUtil.stringToDTO(infoResponse,response,FlightV1Res.class);

        String statusResponse = httpUtil.postFlightV1(flightStatusUrl,request);

        response = JSONUtil.stringToDTO(statusResponse,response,FlightV1Res.class);

        String timeResponse = httpUtil.postFlightV1(flightTimeUrl,request);

        response = JSONUtil.stringToDTO(timeResponse,response,FlightV1Res.class);

        return response;
    }

    @Override
    @LogTime
    public FlightV1Reses searchByRoute(FindByRouteReq request) {
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
        return reses;
    }
}

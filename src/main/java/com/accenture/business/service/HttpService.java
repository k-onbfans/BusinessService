package com.accenture.business.service;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.accenture.business.response.FlightReses;
import com.accenture.business.v1.bean.Port;

public interface HttpService {

    public FlightRes flightResFromFindByFlightNumberReq(String url, FindByFlightNumberReq request);

    public FlightReses flightResesFromFindByRouteReq(String url, FindByRouteReq request);

    public String getFlightV1(String url);

    public String postFlightV1(String url, FindByFlightNumberReq request);

    public String postFlightByRouteV1(String url, Port request);

}

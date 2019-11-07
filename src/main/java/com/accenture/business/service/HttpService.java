package com.accenture.business.service;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.bean.Port;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;

public interface HttpService {

    public FlightV1Res getFlightV1(String url);

    public FlightV1Res postFlightV1(String url, FindByFlightNumberReq request);

    public FlightV1Reses postFlightByRouteV1(String url, Port request);

}

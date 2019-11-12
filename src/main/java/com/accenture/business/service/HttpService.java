package com.accenture.business.service;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.bean.Port;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;

public interface HttpService {

    public FlightV1Res getFlightV1(String url);

    public FlightV1Res postFlightStatusV1(FindByFlightNumberReq request);

    public FlightV1Res postFlightTimeV1(FindByFlightNumberReq request);

    public FlightV1Reses postFlightByRouteV1(Port request);

}

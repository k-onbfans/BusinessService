package com.accenture.business.v1.service;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;

public interface FlightStatusService {

    public FlightV1Res searchByFlightNumber(FindByFlightNumberReq findByFlightNumberReq);

    public FlightV1Reses searchByRoute(FindByRouteReq findByRouteReq);
}

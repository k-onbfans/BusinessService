package com.accenture.business.v2.service;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;

public interface FlightStatusV2Service {

    public FlightV1Reses searchByRoute(FindByRouteReq findByRouteReq);
}

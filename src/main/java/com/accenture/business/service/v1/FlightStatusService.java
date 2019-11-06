package com.accenture.business.service.v1;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;

public interface FlightStatusService {

    public FlightV1Res searchByFlightNumber(FindByFlightNumberReq findByFlightNumberReq);

    public FlightV1Reses searchByRoute(FindByRouteReq findByRouteReq);
}

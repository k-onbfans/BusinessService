package com.accenture.business.service.v2;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.response.v1.FlightV1Res;

import java.util.concurrent.Future;

public interface AsyncService {
    public Future<FlightV1Res> getStatusAndTime (FindByFlightNumberReq req, FlightV1Res res);
}

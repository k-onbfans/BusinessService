package com.accenture.business.service.v2;

import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.v1.FlightV1Reses;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface FlightStatusV2Service {

    public Future<FlightV1Reses> searchByRoute(FindByRouteReq findByRouteReq) throws ExecutionException;
}

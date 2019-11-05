package com.accenture.business.v2.service;

import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.v1.response.FlightV1Reses;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface FlightStatusV2Service {

    public Future<FlightV1Reses> searchByRoute(FindByRouteReq findByRouteReq) throws ExecutionException;
}

package com.accenture.business.service;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MainService {

    FlightRes findByFlightNumberAndDepartureDate(FindByFlightNumberReq request);

    FlightRes findByFlightNumberAndDepartureDate2 (FindByFlightNumberReq request);

    List<FlightRes> findByRoute (FindByRouteReq request);

}

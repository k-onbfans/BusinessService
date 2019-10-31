package com.accenture.business.v1.controller;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;
import com.accenture.business.v1.service.FlightStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/flightstatus")
public class FlightStatusController {

    @Autowired
    private FlightStatusService flightStatusService;

    @PostMapping("/byflightnumber")
    public FlightV1Res searchByFlightNumber(@RequestBody FindByFlightNumberReq findByFlightNumberReq){
        return flightStatusService.searchByFlightNumber(findByFlightNumberReq);
    }

    @PostMapping("/byroute")
    public FlightV1Reses searchByRoute(@RequestBody FindByRouteReq findByRouteReq){
        return flightStatusService.searchByRoute(findByRouteReq);
    }
}

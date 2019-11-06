package com.accenture.business.controller.v2;

import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.v1.FlightV1Reses;
import com.accenture.business.service.v2.FlightStatusV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v2/flightstatus")
public class FlightStatusV2Controller {

    @Autowired
    private FlightStatusV2Service flightStatusV2Service;


    @PostMapping("/byroute")
    public FlightV1Reses searchByRoute(@RequestBody FindByRouteReq findByRouteReq) throws ExecutionException, InterruptedException {

        return flightStatusV2Service.searchByRoute(findByRouteReq);
    }
}

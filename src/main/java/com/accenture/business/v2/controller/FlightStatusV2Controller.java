package com.accenture.business.v2.controller;

import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.v1.response.FlightV1Reses;
import com.accenture.business.v2.service.FlightStatusV2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/v2/flightstatus")
public class FlightStatusV2Controller {

    private static final Logger logger = LoggerFactory.getLogger(FlightStatusV2Controller.class);


    @Autowired
    private FlightStatusV2Service flightStatusV2Service;


    @PostMapping("/byroute")
    public FlightV1Reses searchByRoute(@RequestBody FindByRouteReq findByRouteReq){
        try {
            Future<FlightV1Reses> future = flightStatusV2Service.searchByRoute(findByRouteReq);
            while (true){
                if(future.isDone()){
                    return future.get(20, TimeUnit.SECONDS);
                }
            }
        } catch (ExecutionException | TimeoutException e) {
            logger.error("catch exception");
        }catch (InterruptedException e){
            logger.error("catch InterruptedException");
            Thread.currentThread().interrupt();
        }
        return new FlightV1Reses();
    }
}

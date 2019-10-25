package com.accenture.business.controller;

import com.accenture.business.handler.aop.LogTime;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.accenture.business.service.MainService;
import com.accenture.business.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainService mainService;

    @PostMapping("/findbyflightnumberanddeparturedate")
    public FlightRes findByFlightNumberAndDepartureDate(@RequestBody FindByFlightNumberReq request){
        return mainService.findByFlightNumberAndDepartureDate(request);
    }


    @PostMapping("/findbyflightnumberanddeparturedate2")
    public FlightRes findByFlightNumberAndDepartureDate2 (@RequestBody FindByFlightNumberReq request){
        return mainService.findByFlightNumberAndDepartureDate2(request);
    }


    @PostMapping("/findbyroute")
    public List<FlightRes> findByRoute (@RequestBody FindByRouteReq request){
        return mainService.findByRoute(request);
    }

}

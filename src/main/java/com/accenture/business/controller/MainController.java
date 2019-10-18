package com.accenture.business.controller;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
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
    private HttpUtil httpUtil;

    @Value("${flightInfoByFDUrl}")
    private String flightInfoByFDUrl;

    @Value("${flightStatusByFDUrl}")
    private String flightStatusByFDUrl;

    @Value("${flightTimeByFDUrl}")
    private String flightTimeByFDUrl;

    @Value("${flightByFDUrl}")
    private String flightByFDUrl;

    @Value("${flightInfoByODUrl}")
    private String flightInfoByODUrl;


    @PostMapping("/findbyflightnumberanddeparturedate")
    public FlightRes findByFlightNumberAndDepartureDate(@RequestBody FindByFlightNumberReq request){

        FlightRes flightRes = httpUtil.flightResFromFindByFlightNumberReq(flightInfoByFDUrl,request);

        FlightRes flightRes2 = httpUtil.flightResFromFindByFlightNumberReq(flightStatusByFDUrl,request);
        flightRes.setStatus(flightRes2.getStatus());
        flightRes.setDepartureDate(flightRes2.getDepartureDate());

        FlightRes flightRes3 = httpUtil.flightResFromFindByFlightNumberReq(flightTimeByFDUrl,request);
        flightRes.setEstimatedDepartureTime(flightRes3.getEstimatedDepartureTime());
        flightRes.setEstimatedArrivalTime(flightRes3.getEstimatedArrivalTime());
        flightRes.setScheduledDepartureTime(flightRes3.getScheduledDepartureTime());
        flightRes.setScheduledArrivalTime(flightRes3.getScheduledArrivalTime());

        return flightRes;
    }

    @PostMapping("/findbyflightnumberanddeparturedate2")
    public FlightRes findByFlightNumberAndDepartureDate2 (@RequestBody FindByFlightNumberReq request){
        return httpUtil.flightResFromFindByFlightNumberReq(flightByFDUrl,request);
    }

    @PostMapping("/findbyroute")
    public List<FlightRes> findByRoute (@RequestBody FindByRouteReq request){

        List<FlightRes> list = httpUtil.flightResesFromFindByRouteReq(flightInfoByODUrl,request).getList();


        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        list.forEach(flightRes -> {
            req.setFlightNumber(flightRes.getFlightNumber());

            FlightRes statusresult = httpUtil.flightResFromFindByFlightNumberReq(flightStatusByFDUrl,req);
            flightRes.setStatus(statusresult.getStatus());
            flightRes.setDepartureDate(statusresult.getDepartureDate());

            FlightRes timeresult = httpUtil.flightResFromFindByFlightNumberReq(flightTimeByFDUrl,req);
            flightRes.setScheduledArrivalTime(timeresult.getScheduledArrivalTime());
            flightRes.setScheduledDepartureTime(timeresult.getScheduledDepartureTime());
            flightRes.setEstimatedArrivalTime(timeresult.getEstimatedArrivalTime());
            flightRes.setEstimatedDepartureTime(timeresult.getEstimatedDepartureTime());
        });

        return list;
    }

}

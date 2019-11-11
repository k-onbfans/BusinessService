package com.accenture.business.service.v1.impl;


import com.accenture.business.bean.DepAndArrTime;
import com.accenture.business.bean.Port;
import com.accenture.business.controller.v1.FlightStatusController;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.service.HttpService;
import com.accenture.business.service.v1.FlightStatusService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightStatusServiceImplTest {

    @InjectMocks
    private FlightStatusController flightStatusController;

    @InjectMocks
    private FlightStatusServiceImpl flightStatusServiceImpl;

    @Mock
    private HttpService httpService;


    @Value("${flightStatusByFDV1Url}")
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;


    @Test
    public void test() throws ParseException {
        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setFlightNumber("CX703");
        String str = "2019-11-21";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date =new Date(df.parse(str).getTime());
        req.setDepartureDate(date);
        FlightV1Res flightV1Res = new FlightV1Res();
        flightV1Res.setFlightNumber("CX703");

        Port port = new Port();
        port.setOrigin("HKG");
        port.setDestination("BKK");
        flightV1Res.setPort(port);

        flightV1Res.setAircraft("Boeing 777-300ER(77A)");
        flightV1Res.setDepartureDate("2019-11-21");
        flightV1Res.setStatus("Delayed");

        DepAndArrTime scheduledTime = new DepAndArrTime();
        scheduledTime.setArrivalTime("2019-11-01 18:12:44");
        scheduledTime.setDepartureTime("2019-11-01 18:12:41");
        flightV1Res.setScheduledTime(scheduledTime);

        DepAndArrTime estimatedTime = new DepAndArrTime();
        estimatedTime.setDepartureTime(null);
        estimatedTime.setArrivalTime(null);
        flightV1Res.setEstimatedTime(estimatedTime);

        ArgumentCaptor<String> argumentCaptor1 = ArgumentCaptor.forClass(String.class);

//        flightStatusController.searchByFlightNumber(req);
//        verify(flightStatusServiceImpl,times(1)).searchByFlightNumber(argumentCaptor.capture());
//        Assert.assertEquals(req,argumentCaptor.getValue());
//
//        when(flightStatusServiceImpl.searchByFlightNumber(req)).thenReturn(flightV1Res);
//        Assert.assertEquals(flightV1Res,flightStatusController.searchByFlightNumber(req));
        String flightInfoUrl = "http://localhost:8080/v1/flightinfo/flightnumber/" + req.getFlightNumber();
        flightStatusServiceImpl.searchByFlightNumber(req);
        verify(httpService,times(1)).getFlightV1(argumentCaptor1.capture());
        Assert.assertEquals(flightInfoUrl,argumentCaptor1.getValue());
    }
}
package com.accenture.business.service.v1.impl;

import com.accenture.business.bean.DepAndArrTime;
import com.accenture.business.bean.Port;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;
import com.accenture.business.service.HttpService;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightStatusServiceImplTest {

    @InjectMocks
    private FlightStatusServiceImpl flightStatusServiceImpl;

    @Mock
    private HttpService httpService;

    @Mock
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;



    @Test
    public void testCase1() throws ParseException {
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
//        flightV1Res.setStatus("Delayed");

        DepAndArrTime scheduledTime = new DepAndArrTime();
        scheduledTime.setArrivalTime("2019-11-01 18:12:44");
        scheduledTime.setDepartureTime("2019-11-01 18:12:41");
        flightV1Res.setScheduledTime(scheduledTime);

        DepAndArrTime estimatedTime = new DepAndArrTime();
        estimatedTime.setDepartureTime(null);
        estimatedTime.setArrivalTime(null);
        flightV1Res.setEstimatedTime(estimatedTime);



        ArgumentCaptor<String> argumentCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FindByFlightNumberReq> argumentCaptor2 = ArgumentCaptor.forClass(FindByFlightNumberReq.class);
        String flightInfoUrl = "http://localhost:8080/v1/flightinfo/flightnumber/" + req.getFlightNumber();
//        String flightStatusUrl = "http://localhost:8080/v1/flightstatus/byflightnumber";
//        String flightTimeUrl = "http://localhost:8080/v1/flighttime/byflightnumber";
        when(httpService.getFlightV1(flightInfoUrl)).thenReturn("{\"flightNumber\":\"CX703\",\"port\":{\"origin\":\"HKG\",\"destination\":\"BKK\"},\"aircraft\":\"Boeing 777-300ER(77A)\"}");
        when(httpService.postFlightV1(flightStatusUrl,req)).thenReturn("{\"flightNumber\":\"CX703\",\"departureDate\":\"2019-11-21\",\"status\":\"Delayed\"}");
        when(httpService.postFlightV1(flightTimeUrl,req)).thenReturn("{\"flightNumber\":\"CX703\",\"departureDate\":\"2019-11-21\",\"scheduledTime\":{\"departureTime\":\"2019-11-01 18:12:41\",\"arrivalTime\":\"2019-11-01 18:12:44\"},\"estimatedTime\":{\"departureTime\":null,\"arrivalTime\":null}}");


        flightStatusServiceImpl.searchByFlightNumber(req);
        verify(httpService,times(1)).getFlightV1(argumentCaptor1.capture());
        Assert.assertEquals(flightInfoUrl,argumentCaptor1.getValue());

        Matcher<String> matcher = new ArgumentMatcher<String>() {
            @Override
            public boolean matches(Object argument) {
                if(((String)argument).equals(flightStatusUrl))
                    return true;
                return false;
            }
        };
        verify(httpService,times(2)).postFlightV1(any(String.class),argumentCaptor2.capture());
        Assert.assertEquals(req,argumentCaptor2.getValue());

        Assert.assertEquals(flightV1Res,flightStatusServiceImpl.searchByFlightNumber(req));
    }

//    @Test
    public void testCase2() throws ParseException {
        FindByRouteReq req = new FindByRouteReq();
        String str = "2019-11-21";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date =new Date(df.parse(str).getTime());
        req.setDepartureDate(date);
        req.setDestinationPort("BKK");
        req.setOriginPort("HKG");


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


        FlightV1Res flightV1Res1 = new FlightV1Res();
        flightV1Res1.setFlightNumber("CX707");

        flightV1Res1.setPort(port);

        flightV1Res1.setAircraft("Boeing 777-300ER(77A)");
        flightV1Res1.setDepartureDate("2019-11-21");
        flightV1Res1.setStatus("Delayed");

        DepAndArrTime scheduledTime1 = new DepAndArrTime();
        scheduledTime1.setArrivalTime("2019-11-06 16:52:27");
        scheduledTime1.setDepartureTime("2019-11-06 16:52:31");
        flightV1Res1.setScheduledTime(scheduledTime1);

        flightV1Res1.setEstimatedTime(estimatedTime);

        List<FlightV1Res> list = new ArrayList<>();
        list.add(flightV1Res);
        list.add(flightV1Res1);
        FlightV1Reses flightV1Reses = new FlightV1Reses();
        flightV1Reses.setList(list);
        Port portReq = new Port();


        portReq.setDestination(req.getDestinationPort());
        portReq.setOrigin(req.getOriginPort());
        FindByFlightNumberReq findByFlightNumberReq = new FindByFlightNumberReq();
        findByFlightNumberReq.setDepartureDate(req.getDepartureDate());
        findByFlightNumberReq.setFlightNumber("CX703");

        Matcher<String> matcher = new ArgumentMatcher<String>() {
            @Override
            public boolean matches(Object argument) {
                if(((String)argument).equals("http://localhost:8080/v1/flightinfo/port"))
                    return true;
                return false;
            }
        };
        when(httpService.postFlightByRouteV1(argThat(matcher),eq(portReq))).thenReturn("{\"list\":[{\"flightNumber\":\"CX703\",\"port\":{\"origin\":\"HKG\",\"destination\":\"BKK\"},\"aircraft\":\"Boeing 777-300ER(77A)\"},{\"flightNumber\":\"CX707\",\"port\":{\"origin\":\"HKG\",\"destination\":\"BKK\"},\"aircraft\":\"Boeing 777-300ER(77A)\"}]}");
//        when(httpService.postFlightV1(flightStatusUrl,findByFlightNumberReq)).thenReturn("{\"flightNumber\":\"CX703\",\"departureDate\":\"2019-11-21\",\"status\":\"Delayed\"}");
//        when(httpService.postFlightV1(flightTimeUrl,findByFlightNumberReq)).thenReturn("{\"flightNumber\":\"CX703\",\"departureDate\":\"2019-11-21\",\"scheduledTime\":{\"departureTime\":\"2019-11-01 18:12:41\",\"arrivalTime\":\"2019-11-01 18:12:44\"},\"estimatedTime\":{\"departureTime\":null,\"arrivalTime\":null}}");
        findByFlightNumberReq.setFlightNumber("CX707");
//        when(httpService.postFlightV1(flightStatusUrl,findByFlightNumberReq)).thenReturn("{\"flightNumber\":\"CX707\",\"departureDate\":\"2019-11-21\",\"status\":\"Delayed\"}");
//        when(httpService.postFlightV1(flightTimeUrl,findByFlightNumberReq)).thenReturn("{\"flightNumber\":\"CX707\",\"departureDate\":\"2019-11-21\",\"scheduledTime\":{\"departureTime\":\"2019-11-06 16:52:27\",\"arrivalTime\":\"2019-11-06 16:52:31\"},\"estimatedTime\":{\"departureTime\":null,\"arrivalTime\":null}}");

        ArgumentCaptor<String> argumentCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Port> argumentCaptor2 = ArgumentCaptor.forClass(Port.class);
        flightStatusServiceImpl.searchByRoute(req);
        verify(httpService,times(1)).postFlightByRouteV1(argumentCaptor1.capture(),argumentCaptor2.capture());
//        Assert.assertEquals(flightInfoUrl,argumentCaptor1.getValue());
//
//        Matcher<String> matcher = new ArgumentMatcher<String>() {
//            @Override
//            public boolean matches(Object argument) {
//                if(((String)argument).equals(flightStatusUrl))
//                    return true;
//                return false;
//            }
//        };
//        verify(httpService,times(2)).postFlightV1(any(String.class),argumentCaptor2.capture());
//        Assert.assertEquals(req,argumentCaptor2.getValue());
//
//        Assert.assertEquals(flightV1Res,flightStatusServiceImpl.searchByFlightNumber(req));
    }
}
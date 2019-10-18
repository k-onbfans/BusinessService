package com.accenture.business.utils;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.accenture.business.response.FlightReses;

public interface HttpUtil {
    FlightRes flightResFromFindByFlightNumberReq(String url, FindByFlightNumberReq request);

    FlightReses flightResesFromFindByRouteReq(String url, FindByRouteReq request);

}

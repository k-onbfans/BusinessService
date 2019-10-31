package com.accenture.business.utils;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.response.FlightRes;
import com.accenture.business.response.FlightReses;
import com.accenture.business.v1.bean.Port;
import com.accenture.business.v1.response.FlightV1Res;
import com.accenture.business.v1.response.FlightV1Reses;

public interface HttpUtil {

    public FlightRes flightResFromFindByFlightNumberReq(String url, FindByFlightNumberReq request);

    public FlightReses flightResesFromFindByRouteReq(String url, FindByRouteReq request);

    public String GetFlightV1(String url);

    public String PostFlightV1(String url, FindByFlightNumberReq request);

    public String PostFlightByRouteV1(String url, Port request);

}

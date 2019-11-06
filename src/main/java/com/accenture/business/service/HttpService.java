package com.accenture.business.service;

import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.bean.Port;

public interface HttpService {

    public String getFlightV1(String url);

    public String postFlightV1(String url, FindByFlightNumberReq request);

    public String postFlightByRouteV1(String url, Port request);

}

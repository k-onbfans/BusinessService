package com.accenture.business.request;

import com.accenture.business.handler.validation.IsValidDate;

import java.sql.Date;

public class FindByRouteReq {

    private String originPort;

    private String destinationPort;

    @IsValidDate(future = true)
    private Date departureDate;

    public String getOriginPort() {
        return originPort;
    }

    public void setOriginPort(String originPort) {
        this.originPort = originPort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}

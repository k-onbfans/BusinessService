package com.accenture.business.request;

import java.sql.Date;

public class FindByFlightNumberReq {

    private String flightNumber;

    private Date departureDate;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}

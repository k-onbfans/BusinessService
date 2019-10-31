package com.accenture.business.v1.response;

import com.accenture.business.v1.bean.DepAndArrTime;
import com.accenture.business.v1.bean.Port;

public class FlightV1Res {
    private String flightNumber;
    private Port port;
    private String aircraft;
    private String departureDate;
    private String status;
    private DepAndArrTime scheduledTime;
    private DepAndArrTime estimatedTime;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DepAndArrTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(DepAndArrTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public DepAndArrTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(DepAndArrTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}

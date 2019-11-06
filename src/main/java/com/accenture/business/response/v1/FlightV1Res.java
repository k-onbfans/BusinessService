package com.accenture.business.response.v1;

import com.accenture.business.handler.reflect.TypeReflect;
import com.accenture.business.bean.DepAndArrTime;
import com.accenture.business.bean.Port;

public class FlightV1Res {
    @TypeReflect(value = "flightNumber")
    private String flightNumber;

    @TypeReflect(value = "port")
    private Port port;

    @TypeReflect(value = "aircraft")
    private String aircraft;

    @TypeReflect(value = "departureDate")
    private String departureDate;

    @TypeReflect(value = "status")
    private String status;

    @TypeReflect(value = "scheduledTime")
    private DepAndArrTime scheduledTime;

    @TypeReflect(value = "estimatedTime")
    private DepAndArrTime estimatedTime;

    @TypeReflect(value = "FlightNumber",notnull = true)
    public String getFlightNumber() {
        return flightNumber;
    }

    @TypeReflect(value = "FlightNumber",notnull = true)
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

    public void setDepartureDate(@TypeReflect(value = "departure") String departureDate) {
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

    @TypeReflect(value = "getEstimatedTime",notnull = true)
    public DepAndArrTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(DepAndArrTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
